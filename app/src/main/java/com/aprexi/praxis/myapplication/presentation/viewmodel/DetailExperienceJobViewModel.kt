package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias DeleteExperienceJobUserState = ResourceState<DeleteExperienceJobUser>
typealias UpdateExperienceJobUserState = ResourceState<UpdateExperienceJobUser>
typealias ExperienceJobUserState = ResourceState<ExperienceJobUser>

class DetailExperienceJobViewModel(
    private val deleteExperienceJobUserUseCause: DeleteExperienceJobUserUseCause,
    private val updateExperienceJobUserUseCause: UpdateExperienceJobUserUseCause,
    private val getExperienceJobUserUseCause: GetExperienceJobUserUseCause
) : ViewModel() {
    private val _deleteExperienceJobUserLiveData = MutableLiveData<DeleteExperienceJobUserState>()
    private val _updateExperienceJobUserLiveData = MutableLiveData<UpdateExperienceJobUserState>()
    private val _experienceJobUserLiveData = MutableLiveData<ExperienceJobUserState>()

    fun deleteExperienceJobUserLiveData(): LiveData<DeleteExperienceJobUserState> {
        return _deleteExperienceJobUserLiveData
    }

    fun updateExperienceJobUserLiveData(): LiveData<UpdateExperienceJobUserState> {
        return _updateExperienceJobUserLiveData
    }

    fun experienceJobUserLiveData(): LiveData<ExperienceJobUserState> {
        return _experienceJobUserLiveData
    }

    fun updateExperienceJobUser(
        idUser: Int,
        nameJobs: String,
        level: Int,
        category: Int,
        descriptionJob: String,
        idCompany: Int,
        initDate: String,
        endDate: String,
        idExperienceJobUser: Int,
        token: String
    ) {
        _updateExperienceJobUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateExperienceJobUser = updateExperienceJobUserUseCause.execute(
                    idUser = idUser,
                    nameJobs = nameJobs,
                    level = level,
                    category = category,
                    descriptionJob = descriptionJob,
                    idCompany = idCompany,
                    initDate = initDate,
                    endDate = endDate,
                    idExperienceJobUser = idExperienceJobUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateExperienceJobUser.success) {
                        _updateExperienceJobUserLiveData.value =
                            ResourceState.Success(updateExperienceJobUser)
                    } else {
                        _updateExperienceJobUserLiveData.value =
                            ResourceState.SuccessFaild(updateExperienceJobUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateExperienceJobUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getExperienceJobUser(idUser: Int, idExperienceJobUser: Int, token: String) {
        _experienceJobUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studiesUser = getExperienceJobUserUseCause.execute(
                    idUser = idUser,
                    idExperienceJobUser = idExperienceJobUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (studiesUser.success) {
                        _experienceJobUserLiveData.value =
                            ResourceState.Success(studiesUser)
                    } else {
                        _experienceJobUserLiveData.value =
                            ResourceState.SuccessFaild(studiesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _experienceJobUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteExperienceJobUser(idUser: Int, idExperienceJobUser: Int, token: String) {
        _deleteExperienceJobUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteExperienceJobUser = deleteExperienceJobUserUseCause.execute(
                    idUser = idUser,
                    idExperienceJobUser = idExperienceJobUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (deleteExperienceJobUser.success) {
                        _deleteExperienceJobUserLiveData.value =
                            ResourceState.Success(deleteExperienceJobUser)
                    } else {
                        _deleteExperienceJobUserLiveData.value =
                            ResourceState.SuccessFaild(deleteExperienceJobUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteExperienceJobUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}