package com.aprexi.praxis.myapplication.presentation.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.databinding.FragmentLicenseDetailBinding
import com.aprexi.praxis.myapplication.model.BasicLicense
import com.aprexi.praxis.myapplication.model.DeleteLicenseUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser
import com.aprexi.praxis.myapplication.model.LicenseUser
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteLicenseUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailLicenseViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertLicenseUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LicenseBasicState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LicenseUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateLicenseUserState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LicenseDetailFragment : Fragment() {

    private lateinit var binding: FragmentLicenseDetailBinding
    private lateinit var progressBar: ProgressBar

    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idLicense: Int = 0
    private var idLicenseUser: Int = 0
    private var idFragment: Int = 0
    private val args: LicenseDetailFragmentArgs by navArgs()
    private val licenseViewModel: DetailLicenseViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val myUtils: Utils by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLicenseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pbLicenseDetail
        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()

        if(idFragment == myUtils.CREATE_FRAGMENT){
            initUI()
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)

                if (idFragment == myUtils.MODIFICATE_FRAGMENT) {
                    licenseViewModel.getLicenseUser(
                        idUser = idUser,
                        idLicenseUser = idLicenseUser,
                        token = loginToken
                    )
                } else {

                }

                licenseViewModel.getListBasicLicense(
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(requireContext(), e.toString())
        }
    }

    private fun initArgs() {
        idLicenseUser = args.idLicenseUser
        idLicense = args.idLicense
        idFragment = args.idFragment
    }

    private fun initViewModel() {
        licenseViewModel.getListBasicLicenseLiveData()
            .observe(viewLifecycleOwner, this::handleLicenseBasicState)
        licenseViewModel.licenseUserLiveData().observe(viewLifecycleOwner, this::handleLicenseState)
        licenseViewModel.insertLicenseUserLiveData()
            .observe(viewLifecycleOwner, this::handleInsertLicenseUserState)
        licenseViewModel.deleteLicenseUserLiveData()
            .observe(viewLifecycleOwner, this::handleDeleteLicenseUserState)
        licenseViewModel.updateLicenseUserLiveData()
            .observe(viewLifecycleOwner, this::handleUpdateLicenseUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun getTokenLoginPreference() {
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(
                requireContext(),
                state.error
            ) { cleanTokenAndRedirectToLogin() }

            else -> {}
        }
    }

    private fun handleLicenseState(state: LicenseUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessLicenseUserDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext(), state.error)
            else -> {}
        }
    }

    private fun handleLicenseBasicState(state: LicenseBasicState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListBasicLicense(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext(), state.error)
            else -> {}
        }
    }

    private fun handleUpdateLicenseUserState(state: UpdateLicenseUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessUpdateLicense(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext(), state.error)
            else -> {}
        }
    }

    private fun handleInsertLicenseUserState(state: InsertLicenseUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessInsertLicense(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext(), state.error)
            else -> {}
        }
    }

    private fun handleDeleteLicenseUserState(state: DeleteLicenseUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessDeleteLicense(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext(), state.error)
            else -> {}
        }
    }

    private fun handleSuccessLicenseUserDetail(license: LicenseUser) {
        myUtils.showProgressBar(false, progressBar)

        idLicense = license.idLicense.toInt()
        idLicenseUser = license.idLicenseUser.toInt()

        initUI()
    }

    class LicenseAdapter(
        context: Context,
        resource: Int,
        private val license: List<BasicLicense>
    ) : ArrayAdapter<BasicLicense>(context, resource, license) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val languages = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = languages?.nameLicense // Muestra el nombre del lenguaje
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val license = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                license?.nameLicense // Muestra el nombre del lenguaje en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListBasicLicense(license: ListBasicLicense) {
        myUtils.showProgressBar(false, progressBar)

        val idLic = idLicense

        val adapter = LicenseDetailFragment.LicenseAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            license.license
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsLicenseLicenseDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idLanguage
        val positionOfIdType = license.license.indexOfFirst { it.idLicense.toInt() == idLic }

        // Establece la selección del Spinner a la posición del elemento con idLanguage
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = license.license[position]
                // Realiza alguna acción con el elemento seleccionado
                idLicense = selectedItem.idLicense.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    private fun initUI() {


        binding.vBackBottomLicense.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        when (idFragment) {
            myUtils.MODIFICATE_FRAGMENT -> {
                binding.tvBottonSaveLicenseFragment.visibility = View.VISIBLE
                binding.tvBottonTrushLicenseFragment.visibility = View.VISIBLE
                binding.btnCreateLicenseDetailFragment.visibility = View.GONE
            }

            myUtils.CREATE_FRAGMENT -> {
                binding.tvBottonSaveLicenseFragment.visibility = View.GONE
                binding.tvBottonTrushLicenseFragment.visibility = View.GONE
                binding.btnCreateLicenseDetailFragment.visibility = View.VISIBLE
            }

            else -> {
                // Manejo para otros valores de idFragment si es necesario
            }
        }

        binding.tvBottonSaveLicenseFragment.setOnClickListener {
            updateLicense()
        }

        binding.tvBottonTrushLicenseFragment.setOnClickListener {
            deleteLicense()
        }

        binding.btnCreateLicenseDetailFragment.setOnClickListener {
            createLicense()
        }
    }

    private fun updateLicense() {

        if (idLicenseUser != 0 && idLicense != 0) {
            licenseViewModel.updateLicenseUser(
                idUser = idUser,
                idLicense = idLicense,
                idLicenseUser = idLicenseUser,
                token = loginToken
            )
        } else {
            binding.acsLicenseLicenseDetailFragment.background =
                com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun createLicense() {

        if (idLicense != 0) {
            licenseViewModel.insertLicenseUser(
                idUser = idUser,
                idLicense = idLicense,
                idLicenseUser = idLicenseUser,
                token = loginToken
            )
        } else {
            binding.acsLicenseLicenseDetailFragment.background =
                com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun deleteLicense() {

        myUtils.showConfirmationDialog(
            requireContext(),
            requireContext().getString(com.aprexi.praxis.myapplication.R.string.message_show_dialog_delete)
        ) { confirmed ->
            if (confirmed && idLicense != 0) {
                licenseViewModel.deleteLicenseUser(
                    idUser = idUser,
                    idLicenseUser = idLicenseUser,
                    token = loginToken
                )
            }
        }
    }

    private fun handleSuccessInsertLicense(license: InsertLicenseUser) {
        myUtils.showProgressBar(false, progressBar)

        if (license.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteLicense(license: DeleteLicenseUser) {
        myUtils.showProgressBar(false, progressBar)

        if (license.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessUpdateLicense(languges: UpdateLicenseUser) {
        myUtils.showProgressBar(false, progressBar)

        if (languges.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
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