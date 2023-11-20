package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicLicenseUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertLicenseUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateLicenseUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteLicenseUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser
import com.aprexi.praxis.myapplication.model.LicenseUser
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DeleteLicenseUserState = ResourceState<DeleteLicenseUser>
typealias UpdateLicenseUserState = ResourceState<UpdateLicenseUser>
typealias LicenseUserState = ResourceState<LicenseUser>
typealias InsertLicenseUserState = ResourceState<InsertLicenseUser>
typealias LicenseBasicState = ResourceState<ListBasicLicense>

class DetailLicenseViewModel(
    private val deleteLicenseUserUseCause: DeleteLicenseUserUseCause,
    private val updateLicenseUserUseCause: UpdateLicenseUserUseCause,
    private val insertLicenseUserUseCause: InsertLicenseUserUseCause,
    private val getLicenseUserUseCause: GetLicenseUserUseCause,
    private val getListBasicLicenseUseCause: GetListBasicLicenseUseCause
) : ViewModel() {
    private val _deleteLicenseUserLiveData = MutableLiveData<DeleteLicenseUserState>()
    private val _updateLicenseUserLiveData = MutableLiveData<UpdateLicenseUserState>()
    private val _insertLicenseUserLiveData = MutableLiveData<InsertLicenseUserState>()
    private val _licenseUserLiveData = MutableLiveData<LicenseUserState>()
    private val _basicLicenseLiveData = MutableLiveData<LicenseBasicState>()


    fun getListBasicLicenseLiveData(): LiveData<LicenseBasicState> {
        return _basicLicenseLiveData
    }

    fun insertLicenseUserLiveData(): LiveData<InsertLicenseUserState> {
        return _insertLicenseUserLiveData
    }

    fun deleteLicenseUserLiveData(): LiveData<DeleteLicenseUserState> {
        return _deleteLicenseUserLiveData
    }

    fun updateLicenseUserLiveData(): LiveData<UpdateLicenseUserState> {
        return _updateLicenseUserLiveData
    }

    fun licenseUserLiveData(): LiveData<LicenseUserState> {
        return _licenseUserLiveData
    }


    fun getListBasicLicense(
        token: String
    ) {
        _basicLicenseLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val licenseBasic = getListBasicLicenseUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (licenseBasic.success) {
                        _basicLicenseLiveData.value =
                            ResourceState.Success(licenseBasic)
                    } else {
                        _basicLicenseLiveData.value =
                            ResourceState.SuccessFaild(licenseBasic)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _basicLicenseLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun insertLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ) {
        _insertLicenseUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertLicenseUser = insertLicenseUserUseCause.execute(
                    idUser = idUser,
                    idLicenseUser = idLicenseUser,
                    idLicense = idLicense,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (insertLicenseUser.success) {
                        _insertLicenseUserLiveData.value =
                            ResourceState.Success(insertLicenseUser)
                    } else {
                        _insertLicenseUserLiveData.value =
                            ResourceState.SuccessFaild(insertLicenseUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertLicenseUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ) {
        _updateLicenseUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateLicenseUser = updateLicenseUserUseCause.execute(
                    idUser = idUser,
                    idLicenseUser = idLicenseUser,
                    idLicense = idLicense,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateLicenseUser.success) {
                        _updateLicenseUserLiveData.value =
                            ResourceState.Success(updateLicenseUser)
                    } else {
                        _updateLicenseUserLiveData.value =
                            ResourceState.SuccessFaild(updateLicenseUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateLicenseUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getLicenseUser(idUser: Int, idLicenseUser: Int, token: String) {
        _licenseUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val licenseUser = getLicenseUserUseCause.execute(
                    idUser = idUser,
                    idLicenseUser = idLicenseUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (licenseUser.success) {
                        _licenseUserLiveData.value =
                            ResourceState.Success(licenseUser)
                    } else {
                        _licenseUserLiveData.value =
                            ResourceState.SuccessFaild(licenseUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _licenseUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ) {
        _deleteLicenseUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteLicenseUser = deleteLicenseUserUseCause.execute(
                    idUser = idUser,
                    idLicenseUser = idLicenseUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (deleteLicenseUser.success) {
                        _deleteLicenseUserLiveData.value =
                            ResourceState.Success(deleteLicenseUser)
                    } else {
                        _deleteLicenseUserLiveData.value =
                            ResourceState.SuccessFaild(deleteLicenseUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteLicenseUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}