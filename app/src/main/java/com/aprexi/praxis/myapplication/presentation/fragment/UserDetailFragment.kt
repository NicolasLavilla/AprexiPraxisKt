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
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentUserDetailBinding
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailUserViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.ListBasicMunicipalityState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.UserState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var progressBar: ProgressBar
    private val args: UserDetailFragmentArgs by navArgs()
    private val userViewModel: DetailUserViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val myUtils: Utils by inject()

    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idMunicipality: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pbUserDetail
        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
    }

    private fun initArgs() {
        idUser = args.idUser
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
        userViewModel.updateUserLiveData()
            .observe(viewLifecycleOwner, this::handleUpdateUserState)
        userViewModel.userDataLiveData()
            .observe(viewLifecycleOwner, this::handleUserState)
        userViewModel.getListBasicMunicipalityLiveData()
            .observe(viewLifecycleOwner, this::handleListBasicMunicipalityState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                userViewModel.getUserData(
                    idUser = idUser,
                    token = loginToken
                )

                userViewModel.getListBasicMunicipality()

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showErrorDialog(context = requireContext(), error = e.toString())
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(show = true,progressBar = progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(show = false,progressBar = progressBar)
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(), error = state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleUserState(state: UserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(show = true,progressBar = progressBar)
            is ResourceState.Success -> handleSuccessExperienceJobUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(), error = state.error)
            else -> {}
        }
    }

    private fun handleListBasicMunicipalityState(state: ListBasicMunicipalityState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(show = true,progressBar = progressBar)
            is ResourceState.Success -> handleSuccessListBasicMunicipality(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(), error = state.error)
            else -> {}
        }
    }

    private fun handleUpdateUserState(state: UpdateUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(show = true,progressBar = progressBar)
            is ResourceState.Success -> handleSuccessUpdateUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(), error = state.error)
            else -> {}
        }
    }

    private fun handleSuccessUpdateUser(studies: UpdateUser) {
        myUtils.showProgressBar(show = false,progressBar = progressBar)

        if (studies.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            myUtils.showToast(context = requireContext(), message = "No se ha podido actualizar")
        }
    }

    private fun handleSuccessListBasicMunicipality(municipalityListSuccess: ListBasicMunicipality) {
        desplegableListNameMunicipality(municipalityListSuccess)
        myUtils.showProgressBar(show = false,progressBar = progressBar)
    }

    private fun handleSuccessExperienceJobUser(user: User) {
        myUtils.showProgressBar(show = false,progressBar = progressBar)
        initUI(user)
    }

    private fun initUI(user: User) {
        var birthDate: String = myUtils.changeDateFormatEUR(user.birthDate)
        var gender: Int = 0
        var workPermit: Int = 0
        var autonomousDischarge: Int = 0
        var ownVehicle: Int = 0

        binding.tieNameUserDetailFragment.setText(user.name)
        binding.tieSurnameUserDetailFragment.setText(user.surname1)
        if (!user.surname2.isNullOrBlank()) {
            binding.tieSecondSurnameUserDetailFragment.setText(user.surname2 ?: "")
        }
        if (user.idGender.toInt() == 1) {
            binding.tieGenderMaleUserDetailFragment.isChecked = true
        } else {
            binding.tieGenderFemaleUserDetailFragment.isChecked = true
        }

        binding.tieMobileUserDetailFragment.setText(user.mobile.toString())

        if (!user.dni.isNullOrBlank()) {
            binding.tieDniUserDetailFragment.setText(user.dni ?: "")
        }

        if (!user.nie.isNullOrBlank()) {
            binding.tieNieUserDetailFragment.setText(user.nie ?: "")
        }

        if (!user.passport.isNullOrBlank()) {
            binding.tiePassportUserDetailFragment.setText(user.passport ?: "")
        }

        if (birthDate.isNotEmpty() && birthDate.isNotBlank()) {
            binding.tieBirthDateUserDetailFragment.setText(birthDate ?: "")
        }

        binding.tieBirthDateUserDetailFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieBirthDateUserDetailFragment.setText(selectedDate)
                birthDate = selectedDate
            }
        }
        if (!user.address.isNullOrBlank()) {
            binding.tieAddressUserDetailFragment.setText(user.address ?: "")
        }

        if (!user.description.isNullOrBlank()) {
            binding.tieDescriptionUserDetailFragment.setText(user.description ?: "")
        }

        binding.tieMunicipalityUserDetailFragment.setText(user.nameMunicipality)
        idMunicipality = user.idMunicipality.toInt()
        if (user.workPermit.toInt() == 1) {
            binding.cbWorkPermitUserDetailFragment.isChecked = true
        }
        if (user.autonomousDischarge.toInt() == 1) {
            binding.cbAutonomousDischargeUserDetailFragment.isChecked = true
        }
        if (user.ownVehicle.toInt() == 1) {
            binding.cbOwnVehicleUserDetailFragment.isChecked = true
        }

        binding.vBackBottomUserData.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tvBottonSaveUserDetailFragment.setOnClickListener {

            if (binding.tieGenderMaleUserDetailFragment.isChecked) {
                gender = myUtils.GENDER_MALE
            } else if (binding.tieGenderFemaleUserDetailFragment.isChecked) {
                gender = myUtils.GENDER_FEMALE
            }

            workPermit = if (binding.cbWorkPermitUserDetailFragment.isChecked) {
                1
            } else {
                0
            }

            autonomousDischarge = if (binding.cbAutonomousDischargeUserDetailFragment.isChecked) {
                1
            } else {
                0
            }

            ownVehicle = if (binding.cbOwnVehicleUserDetailFragment.isChecked) {
                1
            } else {
                0
            }

            updateUserData(
                name = myUtils.capitalizeFirstLetter(binding.tieNameUserDetailFragment.text.toString()),
                surname1 = myUtils.capitalizeFirstLetter(binding.tieSurnameUserDetailFragment.text.toString()),
                surname2 = myUtils.capitalizeFirstLetter(binding.tieSecondSurnameUserDetailFragment.text.toString()) ?: "",
                gender = gender,
                mobile = myUtils.capitalizeFirstLetter(binding.tieMobileUserDetailFragment.text.toString()).toInt(),
                dni = myUtils.capitalizeFirstLetter(binding.tieDniUserDetailFragment.text.toString()) ?: "",
                nie = myUtils.capitalizeFirstLetter(binding.tieNieUserDetailFragment.text.toString()) ?: "",
                passport = myUtils.capitalizeFirstLetter(binding.tiePassportUserDetailFragment.text.toString()) ?: "",
                birthDate = birthDate.trim(),
                address = myUtils.capitalizeFirstLetter(binding.tieAddressUserDetailFragment.text.toString()) ?: "",
                municipality = idMunicipality,
                description = myUtils.capitalizeFirstLetter(binding.tieDescriptionUserDetailFragment.text.toString()) ?: "",
                workPermit = workPermit,
                autonomousDischarge = autonomousDischarge,
                ownVehicle = ownVehicle
            )
        }
    }

    private fun updateUserData(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
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
            gender != 0 && myUtils.verificationMobile(mobile, binding.tieMobileUserDetailFragment) &&
            myUtils.verificationDni(dni, binding.tieDniUserDetailFragment) && myUtils.verificationNie(nie, binding.tieNieUserDetailFragment) && myUtils.verificationPassport(passport, binding.tieMobileUserDetailFragment) &&
            (birthDate.isNotEmpty() || birthDate.isNotBlank()) &&
            (address.isNotEmpty() || address.isNotBlank()) &&
            (description.isNotEmpty() || description.isNotBlank()) &&
            municipality != 0
        ) {

            userViewModel.updateUser(
                idUser = idUser,
                name = name,
                surname1 = surname1,
                surname2 = surname2,
                gender = gender,
                mobile = mobile,
                dni = dni,
                nie = nie,
                passport = passport,
                birthDate = myUtils.changeDateFormatEU(birthDate),
                address = address,
                municipality = municipality,
                description = description,
                workPermit = workPermit,
                autonomousDischarge = autonomousDischarge,
                ownVehicle = ownVehicle,
                token = loginToken
            )
        } else {
            if (name.isEmpty() || name.isBlank()) {
                binding.tieNameUserDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (surname1.isEmpty() || surname1.isBlank()) {
                binding.tieSurnameUserDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (birthDate.isEmpty() || birthDate.isBlank()) {
                binding.tieBirthDateUserDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (municipality != 0) {
                binding.tieMunicipalityUserDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(show = false,progressBar = progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun desplegableListNameMunicipality(municipalityListSuccess: ListBasicMunicipality) {
        val autoCompleteTextView = binding.tieMunicipalityUserDetailFragment

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


    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }
}