package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetLicenseListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetUserDataUseCase
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListLicenseUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias ExperienceJobListState = ResourceState<ListExperienceJobUser>
typealias LanguagesUserListState = ResourceState<ListLanguagesUser>
typealias UserState = ResourceState<User>
typealias StudiesUserListState = ResourceState<ListStudiesUser>
typealias ProfessionalProyectsUserListState = ResourceState<ListProfessionalProyectsUser>
typealias LicenseUserListState = ResourceState<ListLicenseUser>
class CurriculumViewModel(
    private val getExperienceJobUserListUseCause: GetExperienceJobUserListUseCause,
    private val getLanguagesListUseCase: GetLanguagesListUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getStudiesListUseCause: GetStudiesListUseCause,
    private val getProfessionalProyectsListUseCause: GetProfessionalProyectsListUseCase,
    private val getLicenseListUseCase: GetLicenseListUseCase
): ViewModel() {

    private val _experienceJobUserLiveData = MutableLiveData<ExperienceJobListState>()
    private val _languagesUserLiveData = MutableLiveData<LanguagesUserListState>()
    private val _userDataLiveData = MutableLiveData<UserState>()
    private val _studiesUserDataLiveData = MutableLiveData<StudiesUserListState>()
    private val _professionalProyectsUserDataLiveData = MutableLiveData<ProfessionalProyectsUserListState>()
    private val _licenseUserDataLiveData = MutableLiveData<LicenseUserListState>()


    fun getLicenseUserLiveData(): LiveData<LicenseUserListState> {
        return _licenseUserDataLiveData
    }
    fun getExperienceJobUserLiveData(): LiveData<ExperienceJobListState> {
        return _experienceJobUserLiveData
    }
    fun getLanguagesUserLiveData(): LiveData<LanguagesUserListState> {
        return _languagesUserLiveData
    }
    fun getUserDataLiveData(): LiveData<UserState> {
        return _userDataLiveData
    }
    fun getStudiesUserLiveData(): LiveData<StudiesUserListState> {
        return _studiesUserDataLiveData
    }
    fun getProfessionalProyectsUserLiveData(): LiveData<ProfessionalProyectsUserListState> {
        return _professionalProyectsUserDataLiveData
    }



    fun fetchLicenseUser(idUser: Int , token: String) {
        _licenseUserDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studies = getLicenseListUseCase.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (studies.success){
                        _licenseUserDataLiveData.value = ResourceState.Success(studies)
                    }else{
                        _licenseUserDataLiveData.value = ResourceState.SuccessFaild(studies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _licenseUserDataLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchProfessionalProyectsUser(idUser: Int , token: String) {
        _professionalProyectsUserDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studies = getProfessionalProyectsListUseCause.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (studies.success){
                        _professionalProyectsUserDataLiveData.value = ResourceState.Success(studies)
                    }else{
                        _professionalProyectsUserDataLiveData.value = ResourceState.SuccessFaild(studies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _professionalProyectsUserDataLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchStudiesUser(idUser: Int , token: String) {
        _studiesUserDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studies = getStudiesListUseCause.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (studies.success){
                        _studiesUserDataLiveData.value = ResourceState.Success(studies)
                    }else{
                        _studiesUserDataLiveData.value = ResourceState.SuccessFaild(studies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _studiesUserDataLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchLanguageUser(idUser: Int , token: String) {
        _languagesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val languages = getLanguagesListUseCase.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (languages.success){
                        _languagesUserLiveData.value = ResourceState.Success(languages)
                    }else{
                        _languagesUserLiveData.value = ResourceState.SuccessFaild(languages)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _languagesUserLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchExperienceJobUser(idUser: Int , token: String) {
        _experienceJobUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val experienceJobsUser = getExperienceJobUserListUseCause.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (experienceJobsUser.success){
                        _experienceJobUserLiveData.value = ResourceState.Success(experienceJobsUser)
                    }else{
                        _experienceJobUserLiveData.value = ResourceState.SuccessFaild(experienceJobsUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _experienceJobUserLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchUserData(idUser: Int , token: String) {
        _userDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = getUserDataUseCase.execute(idUser = idUser, token = token)

                withContext(Dispatchers.Main) {
                    if (user.success){
                        _userDataLiveData.value = ResourceState.Success(user)
                    }else{
                        _userDataLiveData.value = ResourceState.SuccessFaild(user)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _userDataLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}