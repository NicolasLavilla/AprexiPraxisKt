package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentCurriculumBinding
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.User
import com.aprexi.praxis.myapplication.presentation.ExperienceJobsUserDetailActivity
import com.aprexi.praxis.myapplication.presentation.LanguagesDetailActivity
import com.aprexi.praxis.myapplication.presentation.ProfessionalProyectsDetailActivity
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.StudiesDetailActivity
import com.aprexi.praxis.myapplication.presentation.UserDataDetailActivity
import com.aprexi.praxis.myapplication.presentation.adpter.ExperienceJobListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.LanguagesListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.ProfessionalProyectsListAdapter
import com.aprexi.praxis.myapplication.presentation.adpter.StudiesListAdapter
import com.aprexi.praxis.myapplication.presentation.viewmodel.CurriculumViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.ExperienceJobListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LanguagesUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ProfessionalProyectsUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.StudiesUserListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UserState
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurriculumFragment: Fragment() {

    private val binding: FragmentCurriculumBinding by lazy {
        FragmentCurriculumBinding.inflate(layoutInflater)
    }

    private val studiesListAdapter = StudiesListAdapter()
    private val languagesListAdapter = LanguagesListAdapter()
    private val professioanlProyectsListAdapter = ProfessionalProyectsListAdapter()
    private val experienceJobListAdapter = ExperienceJobListAdapter()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val curriculumViewModel: CurriculumViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0

    private lateinit var studies: ListStudiesUser
    private lateinit var experienceJob: ListExperienceJobUser
    private lateinit var languages: ListLanguagesUser
    private lateinit var professionalProyects: ListProfessionalProyectsUser
    private lateinit var userData: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            showErrorDialog(e.toString())
        }
    }

    private fun initViewModel() {
        curriculumViewModel.getUserDataLiveData().observe(viewLifecycleOwner, this::handleUserState)
        curriculumViewModel.getExperienceJobUserLiveData().observe(viewLifecycleOwner, this::handleExperienceJobUserState)
        curriculumViewModel.getLanguagesUserLiveData().observe(viewLifecycleOwner, this::handleLanguagesUserState)
        curriculumViewModel.getStudiesUserLiveData().observe(viewLifecycleOwner, this::handleStudiesUserState)
        curriculumViewModel.getProfessionalProyectsUserLiveData().observe(viewLifecycleOwner, this::handleProfessionalProyectsUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleUserState(state: UserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleExperienceJobUserState(state: ExperienceJobListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleLanguagesUserState(state: LanguagesUserListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleProfessionalProyectsUserState(state: ProfessionalProyectsUserListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleStudiesUserState(state: StudiesUserListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { cleanTokenAndRedirectToLogin() }
            else -> { }
        }
    }

    private fun handleSuccessOfferDetail(user: User) {
        showProgressBar(false)
        userData = user
        initUI()
    }

    private fun handleSuccessOfferDetail(experienceUser: ListExperienceJobUser) {
        showProgressBar(false)
        experienceJob = experienceUser
        experienceJobListAdapter.submitList(experienceUser.experienceJobUser)
    }

    private fun handleSuccessOfferDetail(professionalProUser: ListProfessionalProyectsUser) {
        showProgressBar(false)
        professionalProyects = professionalProUser
        professioanlProyectsListAdapter.submitList(professionalProUser.professionalProyectsUser)
    }

    private fun handleSuccessOfferDetail(languagesUser: ListLanguagesUser) {
        showProgressBar(false)
        languages = languagesUser
        languagesListAdapter.submitList(languagesUser.languagesUser)
    }

    private fun handleSuccessOfferDetail(studiesUser: ListStudiesUser) {
        showProgressBar(false)
        studies = studiesUser
        studiesListAdapter.submitList(studiesUser.studiesUser)
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
            if (userData.surname2.isNotEmpty()) {
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
        binding.tvDateBirthdayUserCurriculumFragment.text = userData.birthDate


        studiesListAdapter.onClickListener = { studies ->

            val intent = Intent(context, StudiesDetailActivity::class.java)
            intent.putExtra("idStudiesUser", studies.idStudiesUser.toInt())
            intent.putExtra("idNameStudies", studies.idNameStudies.toInt())
            intent.putExtra("typeStudies", studies.typeStudies.toInt())
            intent.putExtra("professionalFamilies", studies.professionalFamilies.toInt())
            intent.putExtra("idSchool", studies.idSchool.toInt())
            intent.putExtra("startYear", studies.startYear)
            intent.putExtra("endYear", studies.endYear)
            intent.putExtra("idFragment", 1)// 1 -> Modificar
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
            intent.putExtra("idFragment", 2) // 2 -> Crear
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
            startActivity(intent)
        }

        languagesListAdapter.onClickListener = { language ->

            val intent = Intent(context, LanguagesDetailActivity::class.java)
            intent.putExtra("idLanguages", language.idLanguages.toInt())
            intent.putExtra("idExperience", language.idExperience.toInt())
            intent.putExtra("idFragment", 1) // 1 -> Modificar
            startActivity(intent)
        }

        binding.vInsertLanguagesCurriculumFragment.setOnClickListener {

            val intent = Intent(context, LanguagesDetailActivity::class.java)
            intent.putExtra("idLanguages", 1)
            intent.putExtra("idExperience", 1)
            intent.putExtra("idFragment", 2) // 2 -> Crear
            startActivity(intent)
        }


        professioanlProyectsListAdapter.onClickListener = { professionalProyects ->

            val intent = Intent(context, ProfessionalProyectsDetailActivity::class.java)
            intent.putExtra("idProfessionalProyects", professionalProyects.idProfessionalProyects.toInt())
            startActivity(intent)
        }






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

    private fun showProgressBar(show: Boolean) {
        binding.pbCurriculumFragment.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

}