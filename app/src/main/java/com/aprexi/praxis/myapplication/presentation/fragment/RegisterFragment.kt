package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentRegisterBinding
import com.aprexi.praxis.myapplication.model.InsertUser
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ListBasicMunicipalityState
import com.aprexi.praxis.myapplication.presentation.viewmodel.RegisterViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val registerViewModel: RegisterViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()


    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idMunicipality: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pbRegisterFragment
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
    }

    private fun getTokenLoginPreference() {
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun initViewModel() {
        registerViewModel.insertUserLiveData()
            .observe(viewLifecycleOwner, this::handleInsertUserState)
        registerViewModel.getListBasicMunicipalityLiveData()
            .observe(viewLifecycleOwner, this::handleListBasicMunicipalityState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleAuthentication() {
        registerViewModel.getListBasicMunicipality()
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error) { }
            else -> {}
        }
    }

    private fun handleListBasicMunicipalityState(state: ListBasicMunicipalityState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListBasicMunicipality(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleInsertUserState(state: InsertUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessInsertUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed(state.result)
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleSuccessInsertUser(insertUser: InsertUser) {
        myUtils.showProgressBar(false, progressBar)

        if (insertUser.success) {
            myUtils.redirectToLogin(requireContext())
        } else {
            myUtils.showToast(requireContext(), "No se ha podido registrar!")
        }
    }

    private fun handleSuccessListBasicMunicipality(municipalityListSuccess: ListBasicMunicipality) {
        desplegableListNameMunicipality(municipalityListSuccess)
        myUtils.showProgressBar(false, progressBar)
        initUI()
    }

    private fun handleSuccessFailed(insertUser: InsertUser) {
        myUtils.showProgressBar(false, progressBar)
        myUtils.showToast(requireContext(), insertUser.messageError.toString())
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(false, progressBar)
    }

    private fun initUI() {
        var birthDate: String = ""
        var gender: Int = 0
        var workPermit: Int = 0
        var autonomousDischarge: Int = 0
        var ownVehicle: Int = 0


        binding.tieGenderMaleRegisterFragment.isChecked = true

        binding.vBackBottomRegisterFragment.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tieBirthdayRegisterFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieBirthdayRegisterFragment.setText(selectedDate)
                birthDate = selectedDate
            }
        }

        binding.btnCompleteRegisterFragment.setOnClickListener {

            if (binding.tieGenderMaleRegisterFragment.isChecked) {
                gender = myUtils.GENDER_MALE
            } else if (binding.tieGenderFemaleRegisterFragment.isChecked) {
                gender = myUtils.GENDER_FEMALE
            }

            workPermit = if (binding.cbWorkPermitRegisterFragment.isChecked) { 1 } else { 0 }

            autonomousDischarge = if (binding.cbAutonomousDischargeRegisterFragment.isChecked) { 1 } else { 0 }

            ownVehicle = if (binding.cbOwnVehicleRegisterFragment.isChecked) { 1 } else { 0 }

            var password: String = myUtils.capitalizeFirstLetter(binding.tiePasswordRegisterFragment.text.toString())
            val email: String = binding.tieEmailRegisterFragment.text.toString().trim()
            val mobile: Int = myUtils.capitalizeFirstLetter(binding.tieMobileRegisterFragment.text.toString()).toInt()
            val dni: String = myUtils.capitalizeFirstLetter(binding.tieDniRegisterFragment.text.toString()) ?: ""
            val nie: String = myUtils.capitalizeFirstLetter(binding.tieNieRegisterFragment.text.toString()) ?: ""
            val passport: String = myUtils.capitalizeFirstLetter(binding.tiePassportRegisterFragment.text.toString()) ?: ""

            val securityPassword: Boolean = myUtils.verificationSecurityPassword(requireContext(), password)


            if (securityPassword && myUtils.verificationEmail(email,binding.tieEmailRegisterFragment) && myUtils.verificationMobile(mobile, binding.tieMobileRegisterFragment) &&
                myUtils.verificationDni(dni,binding.tieDniRegisterFragment) && myUtils.verificationNie(nie, binding.tieNieRegisterFragment) && myUtils.verificationPassport(passport,binding.tiePassportRegisterFragment) && myUtils.validateAge(requireContext(),birthDate)
            ) {

                password = myUtils.hashPassword(password)

                insertUserData(
                    name = myUtils.capitalizeFirstLetter(binding.tieNameRegisterFragment.text.toString()),
                    surname1 = myUtils.capitalizeFirstLetter(binding.tieSurnameRegisterFragment.text.toString()),
                    surname2 = myUtils.capitalizeFirstLetter(binding.tieSecondSurnameRegisterFragment.text.toString()) ?: "",
                    gender = gender,
                    mobile = mobile,
                    dni = dni,
                    nie = nie,
                    email = email,
                    password = password,
                    passport = passport,
                    birthDate = birthDate,
                    address = myUtils.capitalizeFirstLetter(binding.tieAddressRegisterFragment.text.toString()) ?: "",
                    municipality = idMunicipality,
                    description = myUtils.capitalizeFirstLetter(binding.tieDescriptionRegisterFragment.text.toString()) ?: "",
                    workPermit = workPermit,
                    autonomousDischarge = autonomousDischarge,
                    ownVehicle = ownVehicle
                )

            } else {
                binding.tieSurnameRegisterFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun insertUserData(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
        email: String,
        password: String,
        dni: String,
        nie: String,
        passport: String,
        birthDate: String,
        address: String,
        municipality: Int,
        description: String,
        workPermit: Int,
        autonomousDischarge: Int,
        ownVehicle: Int
    ) {

        if ((name.isNotEmpty() || name.isNotBlank()) &&
            (surname1.isNotEmpty() || surname1.isNotBlank()) &&
            (email.isNotEmpty() || email.isNotBlank()) &&
            (password.isNotEmpty() || password.isNotBlank()) &&
            gender != 0 &&
            (birthDate.isNotEmpty() || birthDate.isNotBlank()) &&
            municipality != 0
        ) {

            registerViewModel.insertUser(
                name = name,
                surname1 = surname1,
                surname2 = surname2,
                gender = gender,
                mobile = mobile,
                dni = dni,
                nie = nie,
                email = email,
                password = password,
                passport = passport,
                birthDate = myUtils.changeDateFormatEU(birthDate),
                address = address,
                municipality = municipality,
                description = description,
                workPermit = workPermit,
                autonomousDischarge = autonomousDischarge,
                ownVehicle = ownVehicle
            )
        } else {
            if (name.isEmpty() || name.isBlank()) {
                binding.tieNameRegisterFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (surname1.isEmpty() || surname1.isBlank()) {
                binding.tieSurnameRegisterFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (birthDate.isEmpty() || birthDate.isBlank()) {
                binding.tieBirthdayRegisterFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (municipality == 0) {
                binding.tieMunicipalityRegisterFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun desplegableListNameMunicipality(municipalityListSuccess: ListBasicMunicipality) {
        val autoCompleteTextView = binding.tieMunicipalityRegisterFragment

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            municipalityListSuccess.municipality.map { it.nameMunicipality })
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val enteredText = autoCompleteTextView.text.toString()
            val matchingCompanies = municipalityListSuccess.municipality.filter {
                it.nameMunicipality.equals(enteredText, ignoreCase = true)
            }

            if (matchingCompanies.isNotEmpty()) {
                val selectedCompany = matchingCompanies.first()
                idMunicipality = selectedCompany.idMunicipality.toInt()
                // Aquí puedes acceder a la empresa seleccionada y sus propiedades (idCompany, nameCompany)
                // selectedCompany.idCompany
                // selectedCompany.nameCompany
            }
        }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val enteredText = s.toString()
                val matchingMunicipalities = municipalityListSuccess.municipality.filter {
                    it.nameMunicipality.contains(
                        enteredText,
                        ignoreCase = true
                    )
                }

                if (matchingMunicipalities.isEmpty()) {
                    // Aquí puedes guardar el texto que ha escrito el usuario en una variable
                    var nameMunicipality = enteredText
                    // Realiza alguna acción con `userInput`, como guardarlo en una variable global
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


}