package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginUseCause
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias LoginDetailState = ResourceState<Login>

class LoginViewModel(
    private val getLoginUseCause: GetLoginUseCause
): ViewModel() {

    private val _loginLiveData = MutableLiveData<LoginDetailState>()

    fun getLoginLiveData(): LiveData<LoginDetailState> {
        return _loginLiveData
    }

    fun fetchLogin(email: String, password: String) {
        _loginLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val offer = getLoginUseCause.execute(email = email,password = password)

                withContext(Dispatchers.Main) {
                    _loginLiveData.value = ResourceState.Success(offer)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _loginLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}