package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicMunicipalityUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetUserDataUseCase
import com.aprexi.praxis.myapplication.domain.usercase.UpdateUserUseCause
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias UpdateUserState = ResourceState<UpdateUser>
typealias ListBasicMunicipalityState = ResourceState<ListBasicMunicipality>

class DetailUserViewModel(
    private val updateUserUseCause: UpdateUserUseCause,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getListBasicMunicipalityUseCause: GetListBasicMunicipalityUseCause,
) : ViewModel() {
    private val _updateUserLiveData = MutableLiveData<UpdateUserState>()
    private val _userDataLiveData = MutableLiveData<UserState>()
    private val _getListBasicMunicipalityDataLiveData = MutableLiveData<ListBasicMunicipalityState>()

    fun updateUserLiveData(): LiveData<UpdateUserState> {
        return _updateUserLiveData
    }

    fun userDataLiveData(): LiveData<UserState> {
        return _userDataLiveData
    }

    fun getListBasicMunicipalityLiveData(): LiveData<ListBasicMunicipalityState> {
        return _getListBasicMunicipalityDataLiveData
    }

    fun getListBasicMunicipality(token: String) {
        _getListBasicMunicipalityDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listBasicMunicipality = getListBasicMunicipalityUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (listBasicMunicipality.success) {
                        _getListBasicMunicipalityDataLiveData.value =
                            ResourceState.Success(listBasicMunicipality)
                    } else {
                        _getListBasicMunicipalityDataLiveData.value =
                            ResourceState.SuccessFaild(listBasicMunicipality)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _getListBasicMunicipalityDataLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateUser(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
        //email: String,
        dni: String,
        nie: String,
        passport: String,
        birthDate: String,
        address: String,
        municipality: Int,
        description: String,
        workPermit: Int,
        autonomousDischarge: Int,
        ownVehicle: Int,
        idUser: Int,
        token: String
    ) {
        _updateUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateUser = updateUserUseCause.execute(
                    idUser = idUser,
                    name = name,
                    surname1 = surname1,
                    surname2 = surname2,
                    gender = gender,
                    mobile = mobile,
                    //email = email,
                    dni = dni,
                    nie = nie,
                    passport = passport,
                    birthDate = birthDate,
                    address = address,
                    municipality = municipality,
                    description = description,
                    workPermit = workPermit,
                    autonomousDischarge = autonomousDischarge,
                    ownVehicle = ownVehicle,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateUser.success) {
                        _updateUserLiveData.value =
                            ResourceState.Success(updateUser)
                    } else {
                        _updateUserLiveData.value =
                            ResourceState.SuccessFaild(updateUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getUserData(idUser: Int, token: String) {
        _userDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userData = getUserDataUseCase.execute(
                    idUser = idUser, token = token
                )

                withContext(Dispatchers.Main) {
                    if (userData.success) {
                        _userDataLiveData.value =
                            ResourceState.Success(userData)
                    } else {
                        _userDataLiveData.value =
                            ResourceState.SuccessFaild(userData)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _userDataLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}