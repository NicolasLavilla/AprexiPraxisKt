package com.aprexi.praxis.myapplication.presentation.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteProfessionalProyectUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailProfessionalProyectsViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertProfessionalProyectsUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ProfessionalProyectsUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateProfessionalProyectUserState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfessionalProyectDetailFragment : Fragment() {

    private val binding: FragmentProfessionalProyectsDetailBinding by lazy {
        FragmentProfessionalProyectsDetailBinding.inflate(layoutInflater)
    }

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
        return binding.root
    }

    private val args: ProfessionalProyectDetailFragmentArgs by navArgs()
    private val detailProfessionalProyectsViewModel: DetailProfessionalProyectsViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
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
                detailProfessionalProyectsViewModel.getProfessionalProyectsUser(
                    idUser = idUser,
                    idProfessionalProyectsUser = idProfessionalProyects,
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            showErrorDialog(e.toString())
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
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessInsertProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleUpdateProfessionalProyectState(state: UpdateProfessionalProyectUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessUpdateProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleDeleteProfessionalProyectUserState(state: DeleteProfessionalProyectUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessDeleteProfessionalProyect(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleProfessionalProyectsUserState(state: ProfessionalProyectsUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessProfessionalProyectsUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleSuccessInsertProfessionalProyect(professionalProyects: InsertProfessionalProyectsUser) {
        showProgressBar(false)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessUpdateProfessionalProyect(professionalProyects: UpdateProfessionalProyectsUser) {
        showProgressBar(false)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteProfessionalProyect(professionalProyects: DeleteProfessionalProyectsUser) {
        showProgressBar(false)

        if (professionalProyects.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessProfessionalProyectsUser(professionalProyects: ProfessionalProyectsUser) {
        showProgressBar(false)
        initUI(professionalProyects)
    }

    private fun initUI(professionalProyects: ProfessionalProyectsUser) {
        var initDate: String = professionalProyects.initDate
        var endDate: String = professionalProyects.endDate ?: ""


        binding.vBackBottomProfessionalProyects.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tieInitDateProfessionalProyectDetailFragment.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tieInitDateProfessionalProyectDetailFragment.setText(selectedDate)
                initDate = selectedDate
            }
        }

        binding.tieEndDateProfessionalProyectDetailFragment.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tieEndDateProfessionalProyectDetailFragment.setText(selectedDate)
                endDate = selectedDate
            }
        }

        when (idFragment) {
            1 -> { //Actualizar
                binding.tvBottonSaveProfessionalProyectsFragment.visibility = View.VISIBLE
                binding.tvBottonTrushProfessionalProyectsFragment.visibility = View.VISIBLE
                binding.btnCreateProfessionalProyectsDetailFragment.visibility = View.GONE
                binding.tieInitDateProfessionalProyectDetailFragment.text = initDate
                binding.tieDescriptionJobProfessionalProyectsDetailFragment.setText(
                    professionalProyects.descriptionProyect
                )
                binding.tieNameTitleJobProfessionalProyectDetailFragment.setText(professionalProyects.nameProyect)
                binding.tieWebProfessionalProyectDetailFragment.setText(professionalProyects.websites)
                binding.tieJobProfessionalProyectDetailFragment.setText(professionalProyects.job)

                if (endDate.isNotEmpty()) {
                    binding.tieEndDateProfessionalProyectDetailFragment.text = endDate
                }
            }

            2 -> {//Crear
                binding.tvBottonTrushProfessionalProyectsFragment.visibility = View.GONE
                binding.tvBottonSaveProfessionalProyectsFragment.visibility = View.GONE
                binding.btnCreateProfessionalProyectsDetailFragment.visibility = View.VISIBLE
            }

            else -> {
                // Manejo para otros valores de idFragment si es necesario
            }
        }

        binding.tvBottonSaveProfessionalProyectsFragment.setOnClickListener {
            updateProfessionalProyectsUser(
                nameProyect = binding.tieNameTitleJobProfessionalProyectDetailFragment.text.toString(),
                descriptionProyect = binding.tieDescriptionJobProfessionalProyectsDetailFragment.text.toString(),
                initDate = initDate,
                endDate = endDate,
                websites = binding.tieWebProfessionalProyectDetailFragment.text.toString(),
                job = binding.tieJobProfessionalProyectDetailFragment.text.toString(),
                idProfessionalProyectUser = idProfessionalProyects
            )
        }

        binding.btnCreateProfessionalProyectsDetailFragment.setOnClickListener {
            createProfessionalProyectsUser(
                nameProyect = binding.tieNameTitleJobProfessionalProyectDetailFragment.text.toString(),
                descriptionProyect = binding.tieDescriptionJobProfessionalProyectsDetailFragment.text.toString(),
                initDate = initDate,
                endDate = endDate,
                websites = binding.tieWebProfessionalProyectDetailFragment.text.toString(),
                job = binding.tieJobProfessionalProyectDetailFragment.text.toString(),
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
                initDate = initDate,
                endDate = endDate,
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
                initDate = initDate,
                endDate = endDate,
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

        if (idProfessionalProyects != 0) {
            detailProfessionalProyectsViewModel.deleteProfessionalProyectUser(
                idUser = idUser,
                idProfessionalProyectUser = idProfessionalProyects,
                token = loginToken
            )
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbProfessionalProyectsDetail.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handleSuccessFailed() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
    }

    private fun showDatePicker(listener: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = sdf.format(selectedDate.time)

                listener.invoke(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }
}