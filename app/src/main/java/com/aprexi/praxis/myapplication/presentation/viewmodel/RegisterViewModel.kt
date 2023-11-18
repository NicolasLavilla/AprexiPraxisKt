package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicMunicipalityUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertUserUseCause
import com.aprexi.praxis.myapplication.model.InsertUser
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias InsertUserState = ResourceState<InsertUser>

class RegisterViewModel(
    private val insertUserUseCause: InsertUserUseCause,
    private val getListBasicMunicipalityUseCause: GetListBasicMunicipalityUseCause
) : ViewModel() {
    private val _insertUserLiveData = MutableLiveData<InsertUserState>()
    private val _getListBasicMunicipalityDataLiveData =
        MutableLiveData<ListBasicMunicipalityState>()

    fun insertUserLiveData(): LiveData<InsertUserState> {
        return _insertUserLiveData
    }

    fun getListBasicMunicipalityLiveData(): LiveData<ListBasicMunicipalityState> {
        return _getListBasicMunicipalityDataLiveData
    }


    fun insertUser(
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
        _insertUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateUser = insertUserUseCause.execute(
                    name = name,
                    surname1 = surname1,
                    surname2 = surname2,
                    gender = gender,
                    mobile = mobile,
                    email = email,
                    password = password,
                    dni = dni,
                    nie = nie,
                    passport = passport,
                    birthDate = birthDate,
                    address = address,
                    municipality = municipality,
                    description = description,
                    workPermit = workPermit,
                    autonomousDischarge = autonomousDischarge,
                    ownVehicle = ownVehicle
                )

                withContext(Dispatchers.Main) {
                    if (updateUser.success) {
                        _insertUserLiveData.value =
                            ResourceState.Success(updateUser)
                    } else {
                        _insertUserLiveData.value =
                            ResourceState.SuccessFaild(updateUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListBasicMunicipality() {
        _getListBasicMunicipalityDataLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listBasicMunicipality = getListBasicMunicipalityUseCause.execute()

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
}