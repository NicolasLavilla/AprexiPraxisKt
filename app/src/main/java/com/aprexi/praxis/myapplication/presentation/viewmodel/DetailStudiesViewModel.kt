package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListProfessionalFamiliesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListSchoolUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListTypeStudiesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetNameStudiesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateStudiesUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.ListNameStudies
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies
import com.aprexi.praxis.myapplication.model.ListSchool
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DeleteStudiesUserState = ResourceState<DeleteStudiesUser>
typealias UpdateStudiesUserState = ResourceState<UpdateStudiesUser>
typealias StudiesUserState = ResourceState<StudiesUser>
typealias TypeStudiesState = ResourceState<ListTypeStudies>
typealias ProfessionalFamiliesState = ResourceState<ListProfessionalFamilies>
typealias SchoolState = ResourceState<ListSchool>
typealias NameStudiesState = ResourceState<ListNameStudies>
typealias InsertStudiesState = ResourceState<InsertStudiesUser>

class DetailStudiesViewModel(
    private val deleteStudiesUserUseCause: DeleteStudiesUserUseCause,
    private val updateStudiesUserUseCause: UpdateStudiesUserUseCause,
    private val getStudiesUserUseCause: GetStudiesUserUseCause,
    private val getListTypeStudiesUseCause: GetListTypeStudiesUseCause,
    private val getListProfessionalFamiliesUseCause: GetListProfessionalFamiliesUseCause,
    private val getListSchoolUseCause: GetListSchoolUseCause,
    private val getListNameStudiesUseCause: GetNameStudiesUseCause,
    private val insertStudiesUseCause: InsertStudiesUserUseCause
) : ViewModel() {
    private val _deleteStudiesUserLiveData = MutableLiveData<DeleteStudiesUserState>()
    private val _updateStudiesUserLiveData = MutableLiveData<UpdateStudiesUserState>()
    private val _studiesUserLiveData = MutableLiveData<StudiesUserState>()
    private val _typeStudiesLiveData = MutableLiveData<TypeStudiesState>()
    private val _professionalFamiliesLiveData = MutableLiveData<ProfessionalFamiliesState>()
    private val _schoolLiveData = MutableLiveData<SchoolState>()
    private val _nameStudiesLiveData = MutableLiveData<NameStudiesState>()
    private val _insertStudiesLiveData = MutableLiveData<InsertStudiesState>()

    fun deleteStudiesUserLiveData(): LiveData<DeleteStudiesUserState> {
        return _deleteStudiesUserLiveData
    }

    fun updateStudiesUserLiveData(): LiveData<UpdateStudiesUserState> {
        return _updateStudiesUserLiveData
    }

    fun studiesUserLiveData(): LiveData<StudiesUserState> {
        return _studiesUserLiveData
    }

    fun professionalFamiliesLiveData(): LiveData<ProfessionalFamiliesState> {
        return _professionalFamiliesLiveData
    }

    fun schoolLiveData(): LiveData<SchoolState> {
        return _schoolLiveData
    }

    fun nameStudiesLiveData(): LiveData<NameStudiesState> {
        return _nameStudiesLiveData
    }

    fun typeStudiesLiveData(): LiveData<TypeStudiesState> {
        return _typeStudiesLiveData
    }

    fun insertStudiesLiveData(): LiveData<InsertStudiesState> {
        return _insertStudiesLiveData
    }

    fun insertStudies(idUser: Int,
                      idNameStudies: Int,
                      startYear: String,
                      endYear: String,
                      idSchool: Int,
                      idStudiesUser: Int?,
                      token: String) {
        _insertStudiesLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertStudies: InsertStudiesUser = insertStudiesUseCause.execute(
                    idUser = idUser,
                    idNameStudies = idNameStudies,
                    startYear = startYear,
                    endYear = endYear,
                    idSchool = idSchool,
                    idStudiesUser = idStudiesUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (insertStudies.success) {
                        _insertStudiesLiveData.value =
                            ResourceState.Success(insertStudies)
                    } else {
                        _insertStudiesLiveData.value =
                            ResourceState.SuccessFaild(insertStudies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertStudiesLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListNameStudies(idTypeStudies: Int, idProfessionalFamilies: Int, token: String) {
        _nameStudiesLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val nameStudies = getListNameStudiesUseCause.execute(
                    idTypeStudies = idTypeStudies,
                    idProfessionalFamilies = idProfessionalFamilies,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (nameStudies.success) {
                        _nameStudiesLiveData.value =
                            ResourceState.Success(nameStudies)
                    } else {
                        _nameStudiesLiveData.value =
                            ResourceState.SuccessFaild(nameStudies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _nameStudiesLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListSchool(token: String) {
        _schoolLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val school = getListSchoolUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (school.success) {
                        _schoolLiveData.value =
                            ResourceState.Success(school)
                    } else {
                        _schoolLiveData.value =
                            ResourceState.SuccessFaild(school)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _schoolLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListProfessionalFamilies(token: String) {
        _professionalFamiliesLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val professionalFamilies = getListProfessionalFamiliesUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (professionalFamilies.success) {
                        _professionalFamiliesLiveData.value =
                            ResourceState.Success(professionalFamilies)
                    } else {
                        _professionalFamiliesLiveData.value =
                            ResourceState.SuccessFaild(professionalFamilies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _professionalFamiliesLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListTypeStudies(token: String) {
        _typeStudiesLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val typeStudies = getListTypeStudiesUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (typeStudies.success) {
                        _typeStudiesLiveData.value =
                            ResourceState.Success(typeStudies)
                    } else {
                        _typeStudiesLiveData.value =
                            ResourceState.SuccessFaild(typeStudies)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _typeStudiesLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int,
        token: String
    ) {
        _updateStudiesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateStudiesUser = updateStudiesUserUseCause.execute(
                    idUser = idUser,
                    idNameStudies = idNameStudies,
                    startYear = startYear,
                    endYear = endYear,
                    idSchool = idSchool,
                    idStudiesUser = idStudiesUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateStudiesUser.success) {
                        _updateStudiesUserLiveData.value =
                            ResourceState.Success(updateStudiesUser)
                    } else {
                        _updateStudiesUserLiveData.value =
                            ResourceState.SuccessFaild(updateStudiesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateStudiesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getStudiesUser(idUser: Int, idStudiesUser: Int, token: String) {
        _studiesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studiesUser = getStudiesUserUseCause.execute(
                    idUser = idUser,
                    idStudiesUser = idStudiesUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (studiesUser.success) {
                        _studiesUserLiveData.value =
                            ResourceState.Success(studiesUser)
                    } else {
                        _studiesUserLiveData.value =
                            ResourceState.SuccessFaild(studiesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _studiesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteStudiesUser(idUser: Int, idStudiesUser: Int, token: String) {
        _deleteStudiesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteStudiesUser = deleteStudiesUserUseCause.execute(
                    idUser = idUser,
                    idStudiesUser = idStudiesUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (deleteStudiesUser.success) {
                        _deleteStudiesUserLiveData.value =
                            ResourceState.Success(deleteStudiesUser)
                    } else {
                        _deleteStudiesUserLiveData.value =
                            ResourceState.SuccessFaild(deleteStudiesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteStudiesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}