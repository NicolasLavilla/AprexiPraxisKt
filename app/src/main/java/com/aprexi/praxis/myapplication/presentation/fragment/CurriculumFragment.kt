package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.databinding.FragmentCurriculumBinding
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListLicenseUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.User
import com.aprexi.praxis.myapplication.presentation.ExperienceJobsUserDetailActivity
import com.aprexi.praxis.myapplication.presentation.LanguagesDetailActivity
import com.aprexi.praxis.myapplication.presentation.LicenseDetailActivity
import com.aprexi.praxis.myapplication.presentation.ProfessionalProyectsDetailActivity
import com.aprexi.praxis.myapplication.presentation.StudiesDetailActivity
import com.aprexi.praxis.myapplication.presentation.UserDataDetailActivity
import com.aprexi.praxis.myapplication.presentation.adpter.ExperienceJobListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.LanguagesListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.LicenseListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.ProfessionalProyectsListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.StudiesListAdapter
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.CurriculumViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.ExperienceJobListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LanguagesUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LicenseUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LicenseUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ProfessionalProyectsUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.StudiesUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UserState
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurriculumFragment: Fragment() {

    private lateinit var binding: FragmentCurriculumBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val studiesListAdapter = StudiesListAdapter(myUtils)
    private val languagesListAdapter = LanguagesListAdapter()
    private val professioanlProyectsListAdapter = ProfessionalProyectsListAdapter()
    private val licenseListAdapter = LicenseListAdapter()
    private val experienceJobListAdapter = ExperienceJobListAdapter(myUtils)
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val curriculumViewModel: CurriculumViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0

    private lateinit var studies: ListStudiesUser
    private lateinit var license: ListLicenseUser
    private lateinit var experienceJob: ListExperienceJobUser
    private lateinit var languages: ListLanguagesUser
    private lateinit var professionalProyects: ListProfessionalProyectsUser
    private lateinit var userData: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurriculumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbCurriculumFragment
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                curriculumViewModel.fetchUserData(
                    idUser = idUser,
                    token = loginToken
                )

                curriculumViewModel.fetchLanguageUser(
                    idUser = idUser,
                    token = loginToken
                )

                curriculumViewModel.fetchExperienceJobUser(
                    idUser = idUser,
                    token = loginToken
                )

                curriculumViewModel.fetchStudiesUser(
                    idUser = idUser,
                    token = loginToken
                )

                curriculumViewModel.fetchProfessionalProyectsUser(
                    idUser = idUser,
                    token = loginToken
                )

                curriculumViewModel.fetchLicenseUser(
                    idUser = idUser,
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(requireContext() ,e.toString())
        }
    }

    private fun initViewModel() {
        curriculumViewModel.getUserDataLiveData().observe(viewLifecycleOwner, this::handleUserState)
        curriculumViewModel.getExperienceJobUserLiveData().observe(viewLifecycleOwner, this::handleExperienceJobUserState)
        curriculumViewModel.getLanguagesUserLiveData().observe(viewLifecycleOwner, this::handleLanguagesUserState)
        curriculumViewModel.getStudiesUserLiveData().observe(viewLifecycleOwner, this::handleStudiesUserState)
        curriculumViewModel.getProfessionalProyectsUserLiveData().observe(viewLifecycleOwner, this::handleProfessionalProyectsUserState)
        curriculumViewModel.getLicenseUserLiveData().observe(viewLifecycleOwner, this::handleLicenseUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleUserState(state: UserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleExperienceJobUserState(state: ExperienceJobListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleLanguagesUserState(state: LanguagesUserListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleProfessionalProyectsUserState(state: ProfessionalProyectsUserListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleLicenseUserState(state: LicenseUserListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleLicenseOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }


    private fun handleStudiesUserState(state: StudiesUserListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error) { cleanTokenAndRedirectToLogin() }
            else -> { }
        }
    }

    private fun handleSuccessOfferDetail(user: User) {
        myUtils.showProgressBar(false, progressBar)
        userData = user
        initUI()
    }

    private fun handleSuccessOfferDetail(experienceUser: ListExperienceJobUser) {
        myUtils.showProgressBar(false, progressBar)
        experienceJob = experienceUser
        experienceJobListAdapter.submitList(experienceUser.experienceJobUser)
    }

    private fun handleSuccessOfferDetail(professionalProUser: ListProfessionalProyectsUser) {
        myUtils.showProgressBar(false, progressBar)
        professionalProyects = professionalProUser
        professioanlProyectsListAdapter.submitList(professionalProUser.professionalProyectsUser)
    }

    private fun handleSuccessOfferDetail(languagesUser: ListLanguagesUser) {
        myUtils.showProgressBar(false, progressBar)
        languages = languagesUser
        languagesListAdapter.submitList(languagesUser.languagesUser)
    }

    private fun handleSuccessOfferDetail(studiesUser: ListStudiesUser) {
        myUtils.showProgressBar(false, progressBar)
        studies = studiesUser
        studiesListAdapter.submitList(studiesUser.studiesUser)
    }

    private fun handleLicenseOfferDetail(licenseUser: ListLicenseUser) {
        myUtils.showProgressBar(false, progressBar)
        license = licenseUser
        licenseListAdapter.submitList(licenseUser.licenseUser)
    }


    private fun initUI() {

        binding.rvListStudiesUserCurriculumFragment.adapter = studiesListAdapter
        binding.rvListStudiesUserCurriculumFragment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvListExperienceUserCurriculumFragment.adapter = experienceJobListAdapter
        binding.rvListExperienceUserCurriculumFragment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvListLanguagesUserCurriculumFragment.adapter = languagesListAdapter
        binding.rvListLanguagesUserCurriculumFragment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvListProyectsUserCurriculumFragment.adapter = professioanlProyectsListAdapter
        binding.rvListProyectsUserCurriculumFragment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvListLicensesUserCurriculumFragment.adapter = licenseListAdapter
        binding.rvListLicensesUserCurriculumFragment.layoutManager = LinearLayoutManager(requireContext())


        if (!userData.image.isNullOrBlank()) {
            Glide.with(requireContext())
                .load(userData.image)
                .into(binding.ivProfileUserCurriculumFragment)
        }

        val fullName = buildString {
            append(userData.name)
            if (userData.surname1.isNotEmpty()) {
                append(" ")
                append(userData.surname1)
            }
            if (userData.surname2.isNotEmpty() && userData.surname2 != null) {
                append(" ")
                append(userData.surname2)
            }
        }

        binding.tvNameUserCurriculumFragment.text = fullName
        binding.tvMobileUserCurriculumFragment.text = userData.mobile.toString()
        binding.tvEmailUserCurriculumFragment.text = userData.email
        binding.tvLocationMunicipalityUserCurriculumFragment.text = userData.nameMunicipality
        binding.tvLocationUserCurriculumFragment.text = userData.nameCcaa
        binding.tvDescriptionUserCurriculumFragment.text = userData.description
        binding.tvCountryUserCurriculumFragment.text = userData.nameCountry
        binding.tvDateBirthdayUserCurriculumFragment.text = myUtils.changeDateFormatEUR(userData.birthDate)


        binding.vBottonMenuCurriculumFragment.setOnClickListener{
            cleanTokenAndRedirectToLogin()
        }

        studiesListAdapter.onClickListener = { studies ->

            val intent = Intent(context, StudiesDetailActivity::class.java)
            intent.putExtra("idStudiesUser", studies.idStudiesUser.toInt())
            intent.putExtra("idNameStudies", studies.idNameStudies.toInt())
            intent.putExtra("typeStudies", studies.typeStudies.toInt())
            intent.putExtra("professionalFamilies", studies.professionalFamilies.toInt())
            intent.putExtra("idSchool", studies.idSchool.toInt())
            intent.putExtra("startYear", studies.startYear)
            intent.putExtra("endYear", studies.endYear)
            intent.putExtra("idFragment",  myUtils.MODIFICATE_FRAGMENT)
            startActivity(intent)
        }

        binding.vInsertStudiesCurriculumFragment.setOnClickListener {

            val intent = Intent(context, StudiesDetailActivity::class.java)
            intent.putExtra("idStudiesUser", 1) //Tiene que recibir un 1 para que sea null +-
            intent.putExtra("idNameStudies", 1)
            intent.putExtra("typeStudies", 1)
            intent.putExtra("professionalFamilies", 1)
            intent.putExtra("idSchool", 1)
            intent.putExtra("startYear", "0000-00-00")
            intent.putExtra("endYear", "0000-00-00")
            intent.putExtra("idFragment",  myUtils.CREATE_FRAGMENT)
            startActivity(intent)
        }

        binding.cvCurriculumUserFragment.setOnClickListener {

            val intent = Intent(context, UserDataDetailActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }

        experienceJobListAdapter.onClickListener = { experience ->

            val intent = Intent(context, ExperienceJobsUserDetailActivity::class.java)
            intent.putExtra("idExperienceJob", experience.idExperienceJobUser.toInt())
            intent.putExtra("idCompany", experience.idCompany.toInt())
            intent.putExtra("idLevelJob", experience.level.toInt())
            intent.putExtra("idCategory", experience.idCategory.toInt())
            intent.putExtra("idFragment", myUtils.MODIFICATE_FRAGMENT)
            startActivity(intent)
        }

        binding.vInsertExperienceCurriculumFragment.setOnClickListener {

            val intent = Intent(context, ExperienceJobsUserDetailActivity::class.java)
            intent.putExtra("idExperienceJob", 1)
            intent.putExtra("idCompany", 1)
            intent.putExtra("idLevelJob", 1)
            intent.putExtra("idCategory", 1)
            intent.putExtra("idFragment", myUtils.CREATE_FRAGMENT)
            startActivity(intent)
        }

        languagesListAdapter.onClickListener = { language ->

            val intent = Intent(context, LanguagesDetailActivity::class.java)
            intent.putExtra("idLanguages", language.idLanguages.toInt())
            intent.putExtra("idExperience", language.idExperience.toInt())
            intent.putExtra("idFragment", myUtils.MODIFICATE_FRAGMENT)
            startActivity(intent)
        }

        binding.vInsertLanguagesCurriculumFragment.setOnClickListener {

            val intent = Intent(context, LanguagesDetailActivity::class.java)
            intent.putExtra("idLanguages", 1)
            intent.putExtra("idExperience", 1)
            intent.putExtra("idFragment", myUtils.CREATE_FRAGMENT)
            startActivity(intent)
        }


        professioanlProyectsListAdapter.onClickListener = { professionalProyects ->

            val intent = Intent(context, ProfessionalProyectsDetailActivity::class.java)
            intent.putExtra("idProfessionalProyects", professionalProyects.idProfessionalProyects.toInt())
            intent.putExtra("idFragment", myUtils.MODIFICATE_FRAGMENT)
            startActivity(intent)
        }

        binding.vInsertProfessionalProyectsCurriculumFragment.setOnClickListener {

            val intent = Intent(context, ProfessionalProyectsDetailActivity::class.java)
            intent.putExtra("idProfessionalProyects", 1)
            intent.putExtra("idFragment", myUtils.CREATE_FRAGMENT)
            startActivity(intent)
        }

        licenseListAdapter.onClickListener = { license ->

            val intent = Intent(context, LicenseDetailActivity::class.java)
            intent.putExtra("idLicense", license.idLicense.toInt())
            intent.putExtra("idLicenseUser", license.idLicenseUser.toInt())
            intent.putExtra("idFragment", myUtils.MODIFICATE_FRAGMENT)
            startActivity(intent)
        }

        binding.vInsertLicensesCurriculumFragment.setOnClickListener {

            val intent = Intent(context, LicenseDetailActivity::class.java)
            intent.putExtra("idLicense", 1)
            intent.putExtra("idLicenseUser", 1)
            intent.putExtra("idFragment", myUtils.CREATE_FRAGMENT)
            startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        handleAuthentication()
    }
}