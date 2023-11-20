package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentProfessionalProyectsDetailBinding
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteProfessionalProyectUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailProfessionalProyectsViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertProfessionalProyectsUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ProfessionalProyectsUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateProfessionalProyectUserState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfessionalProyectDetailFragment : Fragment() {

    private lateinit var binding: FragmentProfessionalProyectsDetailBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var professionalProyects: ProfessionalProyectsUser
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idFragment: Int = 0
    private var idProfessionalProyects: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfessionalProyectsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val args: ProfessionalProyectDetailFragmentArgs by navArgs()
    private val detailProfessionalProyectsViewModel: DetailProfessionalProyectsViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val myUtils: Utils by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pbProfessionalProyectsDetail
        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()

        if (idFragment == myUtils.CREATE_FRAGMENT) {
            initUI()
        }
    }

    private fun initArgs() {
        idProfessionalProyects = args.idProfessionalProyects
        idFragment = args.idFragment
    }

    private fun initViewModel() {
        detailProfessionalProyectsViewModel.insertProfessionalProyectLiveData()
            .observe(viewLifecycleOwner, this::handleInsertProfessionalProyectsUserState)
        detailProfessionalProyectsViewModel.deleteProfessionalProyectLiveData()
            .observe(viewLifecycleOwner, this::handleDeleteProfessionalProyectUserState)
        detailProfessionalProyectsViewModel.updateProfessionalProyectLiveData()
            .observe(viewLifecycleOwner, this::handleUpdateProfessionalProyectState)
        detailProfessionalProyectsViewModel.professionalProyectsUserLiveData()
            .observe(viewLifecycleOwner, this::handleProfessionalProyectsUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                if (idFragment == myUtils.MODIFICATE_FRAGMENT) {
                    detailProfessionalProyectsViewModel.getProfessionalProyectsUser(
                        idUser = idUser,
                        idProfessionalProyectsUser = idProfessionalProyects,
                        token = loginToken
                    )
                }
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(context = requireContext(), error = e.toString())
        }
    }

    private fun getTokenLoginPreference() {
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun handleInsertProfessionalProyectsUserState(state: InsertProfessionalProyectsUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessInsertProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(
                context = requireContext(),
                state.error
            )

            else -> {}
        }
    }

    private fun handleUpdateProfessionalProyectState(state: UpdateProfessionalProyectUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessUpdateProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(
                context = requireContext(),
                state.error
            )

            else -> {}
        }
    }

    private fun handleDeleteProfessionalProyectUserState(state: DeleteProfessionalProyectUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessDeleteProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(
                context = requireContext(),
                state.error
            )

            else -> {}
        }
    }

    private fun handleProfessionalProyectsUserState(state: ProfessionalProyectsUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessProfessionalProyectsUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(
                context = requireContext(),
                state.error
            )

            else -> {}
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(
                context = requireContext(),
                state.error
            ) { cleanTokenAndRedirectToLogin() }

            else -> {}
        }
    }

    private fun handleSuccessInsertProfessionalProyect(professionalProyects: InsertProfessionalProyectsUser) {
        myUtils.showProgressBar(false, progressBar)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessUpdateProfessionalProyect(professionalProyects: UpdateProfessionalProyectsUser) {
        myUtils.showProgressBar(false, progressBar)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteProfessionalProyect(professionalProyects: DeleteProfessionalProyectsUser) {
        myUtils.showProgressBar(false, progressBar)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessProfessionalProyectsUser(professionalProy: ProfessionalProyectsUser) {
        myUtils.showProgressBar(false, progressBar)
        professionalProyects = professionalProy
        initUI()
    }

    private fun initUI() {
        val initDate: String = when (idFragment) {
            myUtils.MODIFICATE_FRAGMENT -> {
                val initDateFormatted = myUtils.changeDateFormatEUR(professionalProyects.initDate)
                binding.apply {
                    tvBottonSaveProfessionalProyectsFragment.visibility = View.VISIBLE
                    tvBottonTrushProfessionalProyectsFragment.visibility = View.VISIBLE
                    btnCreateProfessionalProyectsDetailFragment.visibility = View.GONE
                    tieInitDateProfessionalProyectDetailFragment.text = initDateFormatted
                    tieDescriptionJobProfessionalProyectsDetailFragment.setText(
                        professionalProyects.descriptionProyect
                    )
                    tieNameTitleJobProfessionalProyectDetailFragment.setText(
                        professionalProyects.nameProyect
                    )
                    tieWebProfessionalProyectDetailFragment.setText(professionalProyects.websites)
                    tieJobProfessionalProyectDetailFragment.setText(professionalProyects.job)

                    val endDateFormatted = myUtils.changeDateFormatEUR(professionalProyects.endDate ?: "")
                    if (endDateFormatted.isNotEmpty()) {
                        tieEndDateProfessionalProyectDetailFragment.text = endDateFormatted
                    }
                }
                initDateFormatted
            }
            myUtils.CREATE_FRAGMENT -> {
                binding.apply {
                    tvBottonTrushProfessionalProyectsFragment.visibility = View.GONE
                    tvBottonSaveProfessionalProyectsFragment.visibility = View.GONE
                    btnCreateProfessionalProyectsDetailFragment.visibility = View.VISIBLE
                }
                "" // You might want to initialize this with a default value
            }
            else -> {
                // Handle other values of idFragment if needed
                ""
            }
        }

        binding.vBackBottomProfessionalProyects.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tieInitDateProfessionalProyectDetailFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieInitDateProfessionalProyectDetailFragment.setText(selectedDate)
            }
        }

        binding.tieEndDateProfessionalProyectDetailFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieEndDateProfessionalProyectDetailFragment.setText(selectedDate)
            }
        }

        binding.tvBottonSaveProfessionalProyectsFragment.setOnClickListener {
            updateProfessionalProyectsUser(
                nameProyect = myUtils.capitalizeFirstLetter(binding.tieNameTitleJobProfessionalProyectDetailFragment.text.toString()),
                descriptionProyect = myUtils.capitalizeFirstLetter(binding.tieDescriptionJobProfessionalProyectsDetailFragment.text.toString()),
                initDate = initDate,
                endDate = binding.tieEndDateProfessionalProyectDetailFragment.text.toString(),
                websites = myUtils.capitalizeFirstLetter(binding.tieWebProfessionalProyectDetailFragment.text.toString()),
                job = myUtils.capitalizeFirstLetter(binding.tieJobProfessionalProyectDetailFragment.text.toString()),
                idProfessionalProyectUser = idProfessionalProyects
            )
        }

        binding.btnCreateProfessionalProyectsDetailFragment.setOnClickListener {
            createProfessionalProyectsUser(
                nameProyect = myUtils.capitalizeFirstLetter(binding.tieNameTitleJobProfessionalProyectDetailFragment.text.toString()),
                descriptionProyect = myUtils.capitalizeFirstLetter(binding.tieDescriptionJobProfessionalProyectsDetailFragment.text.toString()),
                initDate = initDate,
                endDate = binding.tieEndDateProfessionalProyectDetailFragment.text.toString(),
                websites = myUtils.capitalizeFirstLetter(binding.tieWebProfessionalProyectDetailFragment.text.toString()),
                job = myUtils.capitalizeFirstLetter(binding.tieJobProfessionalProyectDetailFragment.text.toString()),
                idProfessionalProyectUser = idProfessionalProyects
            )
        }

        binding.tvBottonTrushProfessionalProyectsFragment.setOnClickListener {
            deleteProfessionalProyectsUser()
        }
    }

    private fun createProfessionalProyectsUser(
        idProfessionalProyectUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String
    ) {

        if ((nameProyect.isNotEmpty() || nameProyect.isNotBlank()) &&
            (descriptionProyect.isNotEmpty() || descriptionProyect.isNotBlank())
        ) {
            detailProfessionalProyectsViewModel.insertProfessionalProyectUser(
                idUser = idUser,
                nameProyect = nameProyect,
                descriptionProyect = descriptionProyect,
                initDate = myUtils.changeDateFormatEU(initDate),
                endDate = myUtils.changeDateFormatEU(endDate),
                websites = websites,
                job = job,
                idProfessionalProyectUser = idProfessionalProyectUser,
                token = loginToken
            )
        } else {
            if (nameProyect.isEmpty() || nameProyect.isBlank()) {
                binding.tieNameTitleJobProfessionalProyectDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (descriptionProyect.isEmpty() || descriptionProyect.isBlank()) {
                binding.tieDescriptionJobProfessionalProyectsDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun updateProfessionalProyectsUser(
        idProfessionalProyectUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String
    ) {

        if ((nameProyect.isNotEmpty() || nameProyect.isNotBlank()) &&
            (descriptionProyect.isNotEmpty() || descriptionProyect.isNotBlank())
        ) {
            detailProfessionalProyectsViewModel.updateProfessionalProyectUser(
                idUser = idUser,
                nameProyect = nameProyect,
                descriptionProyect = descriptionProyect,
                initDate = myUtils.changeDateFormatEU(initDate),
                endDate = myUtils.changeDateFormatEU(endDate),
                websites = websites,
                job = job,
                idProfessionalProyectUser = idProfessionalProyectUser,
                token = loginToken
            )
        } else {
            if (nameProyect.isEmpty() || nameProyect.isBlank()) {
                binding.tieNameTitleJobProfessionalProyectDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (descriptionProyect.isEmpty() || descriptionProyect.isBlank()) {
                binding.tieDescriptionJobProfessionalProyectsDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun deleteProfessionalProyectsUser() {

        myUtils.showConfirmationDialog(requireContext(), requireContext().getString(com.aprexi.praxis.myapplication.R.string.message_show_dialog_delete)) { confirmed ->
            if (confirmed && idProfessionalProyects != 0) {
                detailProfessionalProyectsViewModel.deleteProfessionalProyectUser(
                    idUser = idUser,
                    idProfessionalProyectUser = idProfessionalProyects,
                    token = loginToken
                )
            }
        }
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(false, progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }
}