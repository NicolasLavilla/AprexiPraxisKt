package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.DeleteStudiesUserUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetStudiesUserUseCause
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.StudiesUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DeleteStudiesUserState = ResourceState<DeleteStudiesUser>
typealias StudiesUserState = ResourceState<StudiesUser>
class DetailStudiesViewModel(
    private val deleteStudiesUserUseCause: DeleteStudiesUserUseCause,
    private val getStudiesUserUseCause: GetStudiesUserUseCause
): ViewModel() {
    private val _deleteStudiesUserLiveData = MutableLiveData<DeleteStudiesUserState>()
    private val _studiesUserLiveData = MutableLiveData<StudiesUserState>()

    fun deleteStudiesUserLiveData(): LiveData<DeleteStudiesUserState> {
        return _deleteStudiesUserLiveData
    }
    fun studiesUserLiveData(): LiveData<StudiesUserState> {
        return _studiesUserLiveData
    }

    fun getStudiesUser(idUser: Int, idStudiesUser:Int , token: String) {
        _studiesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studiesUser = getStudiesUserUseCause.execute(idUser= idUser, idStudiesUser = idStudiesUser, token= token)

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

    fun deleteStudiesUser(idUser: Int,idStudiesUser:Int, token: String) {
        _deleteStudiesUserLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteStudiesUser = deleteStudiesUserUseCause.execute(idUser= idUser, idStudiesUser = idStudiesUser, token= token)

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