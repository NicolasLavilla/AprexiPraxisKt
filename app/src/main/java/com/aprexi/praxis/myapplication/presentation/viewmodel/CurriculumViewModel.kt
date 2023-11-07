package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsListUseCase
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesListUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetUserDataUseCase
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias ExperienceJobState = ResourceState<ListExperienceJobUser>
typealias LanguagesUserState = ResourceState<ListLanguagesUser>
typealias UserState = ResourceState<User>
typealias StudiesUserState = ResourceState<ListStudiesUser>
typealias ProfessionalProyectsUserState = ResourceState<ListProfessionalProyectsUser>
class CurriculumViewModel(
    private val getExperienceJobUserListUseCause: GetExperienceJobUserListUseCause,
    private val getLanguagesListUseCase: GetLanguagesListUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getStudiesListUseCause: GetStudiesListUseCause,
    private val getProfessionalProyectsListUseCause: GetProfessionalProyectsListUseCase
): ViewModel() {

    private val _experienceJobUserLiveData = MutableLiveData<ExperienceJobState>()
    private val _languagesUserLiveData = MutableLiveData<LanguagesUserState>()
    private val _userDataLiveData = MutableLiveData<UserState>()
    private val _studiesUserDataLiveData = MutableLiveData<StudiesUserState>()
    private val _professionalProyectsUserDataLiveData = MutableLiveData<ProfessionalProyectsUserState>()

    fun getExperienceJobUserLiveData(): LiveData<ExperienceJobState> {
        return _experienceJobUserLiveData
    }
    fun getLanguagesUserLiveData(): LiveData<LanguagesUserState> {
        return _languagesUserLiveData
    }
    fun getUserDataLiveData(): LiveData<UserState> {
        return _userDataLiveData
    }
    fun getStudiesUserLiveData(): LiveData<StudiesUserState> {
        return _studiesUserDataLiveData
    }
    fun getProfessionalProyectsUserLiveData(): LiveData<ProfessionalProyectsUserState> {
        return _professionalProyectsUserDataLiveData
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