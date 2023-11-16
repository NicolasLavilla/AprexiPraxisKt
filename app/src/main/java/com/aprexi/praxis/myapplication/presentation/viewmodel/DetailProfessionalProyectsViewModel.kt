package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetProfessionalProyectsUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateProfessionalProyectUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DeleteProfessionalProyectUserState = ResourceState<DeleteProfessionalProyectsUser>
typealias UpdateProfessionalProyectUserState = ResourceState<UpdateProfessionalProyectsUser>
typealias ProfessionalProyectsUserState = ResourceState<ProfessionalProyectsUser>
typealias InsertProfessionalProyectsUserState = ResourceState<InsertProfessionalProyectsUser>

class DetailProfessionalProyectsViewModel(
    private val deleteProfessionalProyectsUserUseCause: DeleteProfessionalProyectUserUseCause,
    private val updateProfessionalProyectsUserUseCause: UpdateProfessionalProyectUserUseCause,
    private val getProfessionalProyectsUserUseCause: GetProfessionalProyectsUserUseCause,
    private val insertProfessionalProyectsUserCase: InsertProfessionalProyectUserUseCause
) : ViewModel() {
    private val _deleteProfessionalProyectUserLiveData =
        MutableLiveData<DeleteProfessionalProyectUserState>()
    private val _updateProfessionalProyectUserLiveData =
        MutableLiveData<UpdateProfessionalProyectUserState>()
    private val _professionalProyectsUserLiveData = MutableLiveData<ProfessionalProyectsUserState>()
    private val _insertProyectsUserLiveData = MutableLiveData<InsertProfessionalProyectsUserState>()

    fun insertProfessionalProyectLiveData(): LiveData<InsertProfessionalProyectsUserState> {
        return _insertProyectsUserLiveData
    }
    fun deleteProfessionalProyectLiveData(): LiveData<DeleteProfessionalProyectUserState> {
        return _deleteProfessionalProyectUserLiveData
    }

    fun updateProfessionalProyectLiveData(): LiveData<UpdateProfessionalProyectUserState> {
        return _updateProfessionalProyectUserLiveData
    }

    fun professionalProyectsUserLiveData(): LiveData<ProfessionalProyectsUserState> {
        return _professionalProyectsUserLiveData
    }

    fun insertProfessionalProyectUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ) {
        _insertProyectsUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertProfessionalProyectUser = insertProfessionalProyectsUserCase.execute(
                    idUser = idUser,
                    nameProyect = nameProyect,
                    descriptionProyect = descriptionProyect,
                    websites = websites,
                    job = job,
                    initDate = initDate,
                    endDate = endDate,
                    idProfessionalProyectUser = idProfessionalProyectUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (insertProfessionalProyectUser.success) {
                        _insertProyectsUserLiveData.value =
                            ResourceState.Success(insertProfessionalProyectUser)
                    } else {
                        _insertProyectsUserLiveData.value =
                            ResourceState.SuccessFaild(insertProfessionalProyectUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertProyectsUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateProfessionalProyectUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ) {
        _updateProfessionalProyectUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateProfessionalProyectUser = updateProfessionalProyectsUserUseCause.execute(
                    idUser = idUser,
                    nameProyect = nameProyect,
                    descriptionProyect = descriptionProyect,
                    websites = websites,
                    job = job,
                    initDate = initDate,
                    endDate = endDate,
                    idProfessionalProyectUser = idProfessionalProyectUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateProfessionalProyectUser.success) {
                        _updateProfessionalProyectUserLiveData.value =
                            ResourceState.Success(updateProfessionalProyectUser)
                    } else {
                        _updateProfessionalProyectUserLiveData.value =
                            ResourceState.SuccessFaild(updateProfessionalProyectUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateProfessionalProyectUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getProfessionalProyectsUser(idUser: Int, idProfessionalProyectsUser: Int, token: String) {
        _professionalProyectsUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val professionalProyectsUser = getProfessionalProyectsUserUseCause.execute(
                    idUser = idUser,
                    idProfessionalProyectsUser = idProfessionalProyectsUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (professionalProyectsUser.success) {
                        _professionalProyectsUserLiveData.value =
                            ResourceState.Success(professionalProyectsUser)
                    } else {
                        _professionalProyectsUserLiveData.value =
                            ResourceState.SuccessFaild(professionalProyectsUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _professionalProyectsUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteProfessionalProyectUser(idUser: Int, idProfessionalProyectUser: Int, token: String) {
        _deleteProfessionalProyectUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteProfessionalProyectUser = deleteProfessionalProyectsUserUseCause.execute(
                    idUser = idUser,
                    idProfessionalProyectUser = idProfessionalProyectUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (deleteProfessionalProyectUser.success) {
                        _deleteProfessionalProyectUserLiveData.value =
                            ResourceState.Success(deleteProfessionalProyectUser)
                    } else {
                        _deleteProfessionalProyectUserLiveData.value =
                            ResourceState.SuccessFaild(deleteProfessionalProyectUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteProfessionalProyectUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}