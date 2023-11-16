package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListCategoryUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListCompanyUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListLevelUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateExperienceJobUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListBasicCompany
import com.aprexi.praxis.myapplication.model.ListCategory
import com.aprexi.praxis.myapplication.model.ListLevelJob
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias DeleteExperienceJobUserState = ResourceState<DeleteExperienceJobUser>
typealias UpdateExperienceJobUserState = ResourceState<UpdateExperienceJobUser>
typealias ExperienceJobUserState = ResourceState<ExperienceJobUser>
typealias LevelJobState = ResourceState<ListLevelJob>
typealias CategoryState = ResourceState<ListCategory>
typealias CompanyBasicState = ResourceState<ListBasicCompany>
typealias InsertExpereienceJobUserState = ResourceState<InsertExperienceJobUser>

class DetailExperienceJobViewModel(
    private val deleteExperienceJobUserUseCause: DeleteExperienceJobUserUseCause,
    private val updateExperienceJobUserUseCause: UpdateExperienceJobUserUseCause,
    private val getExperienceJobUserUseCause: GetExperienceJobUserUseCause,
    private val getListLevelUseCause: GetListLevelUseCause,
    private val getListCategoryUseCause: GetListCategoryUseCause,
    private val getListCompanyUseCause: GetListCompanyUseCause,
    private val insertExperienceJobUserUseCause: InsertExperienceJobUserUseCause
) : ViewModel() {
    private val _deleteExperienceJobUserLiveData = MutableLiveData<DeleteExperienceJobUserState>()
    private val _updateExperienceJobUserLiveData = MutableLiveData<UpdateExperienceJobUserState>()
    private val _experienceJobUserLiveData = MutableLiveData<ExperienceJobUserState>()
    private val _levelJobLiveData = MutableLiveData<LevelJobState>()
    private val _categoryLiveData = MutableLiveData<CategoryState>()
    private val _companyLiveData = MutableLiveData<CompanyBasicState>()
    private val _insertExperienceJobUserLiveData = MutableLiveData<InsertExpereienceJobUserState>()

    fun insertExperienceJobUserLiveData(): LiveData<InsertExpereienceJobUserState> {
        return _insertExperienceJobUserLiveData
    }
    fun companyBasicLiveData(): LiveData<CompanyBasicState> {
        return _companyLiveData
    }
    fun categoryLiveData(): LiveData<CategoryState> {
        return _categoryLiveData
    }

    fun levelJobLiveData(): LiveData<LevelJobState> {
        return _levelJobLiveData
    }

    fun deleteExperienceJobUserLiveData(): LiveData<DeleteExperienceJobUserState> {
        return _deleteExperienceJobUserLiveData
    }

    fun updateExperienceJobUserLiveData(): LiveData<UpdateExperienceJobUserState> {
        return _updateExperienceJobUserLiveData
    }

    fun experienceJobUserLiveData(): LiveData<ExperienceJobUserState> {
        return _experienceJobUserLiveData
    }


    fun insertExperienceJobUser(
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
        _insertExperienceJobUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateExperienceJobUser = insertExperienceJobUserUseCause.execute(
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
                        _insertExperienceJobUserLiveData.value =
                            ResourceState.Success(updateExperienceJobUser)
                    } else {
                        _insertExperienceJobUserLiveData.value =
                            ResourceState.SuccessFaild(updateExperienceJobUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertExperienceJobUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListCompany(token: String) {
        _companyLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val basicCompany = getListCompanyUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (basicCompany.success) {
                        _companyLiveData.value =
                            ResourceState.Success(basicCompany)
                    } else {
                        _companyLiveData.value =
                            ResourceState.SuccessFaild(basicCompany)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _companyLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListCategory( token: String) {
        _categoryLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val category = getListCategoryUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (category.success) {
                        _categoryLiveData.value =
                            ResourceState.Success(category)
                    } else {
                        _categoryLiveData.value =
                            ResourceState.SuccessFaild(category)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _categoryLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListLevelJob( token: String) {
        _levelJobLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val levelJob = getListLevelUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (levelJob.success) {
                        _levelJobLiveData.value =
                            ResourceState.Success(levelJob)
                    } else {
                        _levelJobLiveData.value =
                            ResourceState.SuccessFaild(levelJob)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _levelJobLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateExperienceJobUser(
        idUser: Int,
        nameJobs: String,
        level: Int,
        category: Int,
        descriptionJob: String,
        idCompany: Int,
        initDate: String,
        endDate: String?,
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