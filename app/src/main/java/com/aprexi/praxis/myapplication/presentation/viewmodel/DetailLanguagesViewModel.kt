package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListExperienceUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetListBasicLanguagesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.InsertLanguagesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.UpdateLanguagesUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DeleteLanguagesUserState = ResourceState<DeleteLanguagesUser>
typealias UpdateLanguagesUserState = ResourceState<UpdateLanguagesUser>
typealias LanguagesUserState = ResourceState<LanguagesUser>
typealias InsertLanguagesUserState = ResourceState<InsertLanguagesUser>
typealias ExperienceState = ResourceState<ListExperience>
typealias LanguagesBasicState = ResourceState<ListBasicLanguages>

class DetailLanguagesViewModel(
    private val deleteLanguagesUserUseCause: DeleteLanguagesUserUseCause,
    private val updateLanguagesUserUseCause: UpdateLanguagesUserUseCause,
    private val getLanguagesUserUseCause: GetLanguagesUserUseCause,
    private val insertLanguagesUserUseCause: InsertLanguagesUserUseCause,
    private val getListExperienceUseCause: GetListExperienceUseCause,
    private val getListBasicLanguagesBasicUseCause: GetListBasicLanguagesUseCause
) : ViewModel() {
    private val _deleteLanguagesUserLiveData = MutableLiveData<DeleteLanguagesUserState>()
    private val _updateLanguagesUserLiveData = MutableLiveData<UpdateLanguagesUserState>()
    private val _languagesUserLiveData = MutableLiveData<LanguagesUserState>()
    private val _insertLanguagesUserLiveData = MutableLiveData<InsertLanguagesUserState>()
    private val _experienceLiveData = MutableLiveData<ExperienceState>()
    private val _basicLanguagesLiveData = MutableLiveData<LanguagesBasicState>()


    fun getListBasicLangaugesLiveData(): LiveData<LanguagesBasicState> {
        return _basicLanguagesLiveData
    }
    fun getListExperienceLiveData(): LiveData<ExperienceState> {
        return _experienceLiveData
    }
    fun insertLanguagesUserLiveData(): LiveData<InsertLanguagesUserState> {
        return _insertLanguagesUserLiveData
    }

    fun deleteLanguagesUserLiveData(): LiveData<DeleteLanguagesUserState> {
        return _deleteLanguagesUserLiveData
    }

    fun updateLanguagesUserLiveData(): LiveData<UpdateLanguagesUserState> {
        return _updateLanguagesUserLiveData
    }

    fun languagesUserLiveData(): LiveData<LanguagesUserState> {
        return _languagesUserLiveData
    }


    fun getListBasicLanguages(
        token: String
    ) {
        _basicLanguagesLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val languagesBasic = getListBasicLanguagesBasicUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (languagesBasic.success) {
                        _basicLanguagesLiveData.value =
                            ResourceState.Success(languagesBasic)
                    } else {
                        _basicLanguagesLiveData.value =
                            ResourceState.SuccessFaild(languagesBasic)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _basicLanguagesLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getListExperience(
        token: String
    ) {
        _experienceLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val experience = getListExperienceUseCause.execute(
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (experience.success) {
                        _experienceLiveData.value =
                            ResourceState.Success(experience)
                    } else {
                        _experienceLiveData.value =
                            ResourceState.SuccessFaild(experience)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _experienceLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun insertLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ) {
        _insertLanguagesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertLanguagesUser = insertLanguagesUserUseCause.execute(
                    idUser = idUser,
                    idExperience = idExperience,
                    idLanguagesUser = idLanguagesUser,
                    idLanguages = idLanguages,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (insertLanguagesUser.success) {
                        _insertLanguagesUserLiveData.value =
                            ResourceState.Success(insertLanguagesUser)
                    } else {
                        _insertLanguagesUserLiveData.value =
                            ResourceState.SuccessFaild(insertLanguagesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _insertLanguagesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun updateLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ) {
        _updateLanguagesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateLanguagesUser = updateLanguagesUserUseCause.execute(
                    idUser = idUser,
                    idExperience = idExperience,
                    idLanguagesUser = idLanguagesUser,
                    idLanguages = idLanguages,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (updateLanguagesUser.success) {
                        _updateLanguagesUserLiveData.value =
                            ResourceState.Success(updateLanguagesUser)
                    } else {
                        _updateLanguagesUserLiveData.value =
                            ResourceState.SuccessFaild(updateLanguagesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _updateLanguagesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getLanguagesUser(idUser: Int, idLanguages: Int, token: String) {
        _languagesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val languagesUser = getLanguagesUserUseCause.execute(
                    idUser = idUser,
                    idLanguages = idLanguages,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (languagesUser.success) {
                        _languagesUserLiveData.value =
                            ResourceState.Success(languagesUser)
                    } else {
                        _languagesUserLiveData.value =
                            ResourceState.SuccessFaild(languagesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _languagesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteLanguagesUser(idUser: Int, idLanguagesUser: Int, token: String) {
        _deleteLanguagesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteLanguagesUser = deleteLanguagesUserUseCause.execute(
                    idUser = idUser,
                    idLanguagesUser = idLanguagesUser,
                    token = token
                )

                withContext(Dispatchers.Main) {
                    if (deleteLanguagesUser.success) {
                        _deleteLanguagesUserLiveData.value =
                            ResourceState.Success(deleteLanguagesUser)
                    } else {
                        _deleteLanguagesUserLiveData.value =
                            ResourceState.SuccessFaild(deleteLanguagesUser)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteLanguagesUserLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}