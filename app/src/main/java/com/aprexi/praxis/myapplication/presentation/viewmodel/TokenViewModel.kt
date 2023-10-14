package com.aprexi.praxis.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aprexi.praxis.myapplication.domain.usercase.CleanTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetCheckTokenUseCause
import com.aprexi.praxis.myapplication.domain.usercase.GetLoginTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.domain.usercase.SaveTokenPreferencesUseCause
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias TokenDetailState = ResourceState<CheckToken>

class TokenViewModel(
    private val saveTokenPreferences: SaveTokenPreferencesUseCause,
    private val cleanTokenPreferences: CleanTokenPreferencesUseCause,
    private val checkTokenUseCause: GetCheckTokenUseCause,
    private val checkTokenPreferencesUseCause: GetCheckTokenPreferencesUseCause,
    private val checkLoginPreferencesUseCause: GetLoginTokenPreferencesUseCause
) : ViewModel() {

    private val _tokenLiveData = MutableLiveData<TokenDetailState>()

    fun getTokenLiveData(): LiveData<TokenDetailState> {
        return _tokenLiveData
    }

    fun fetchCheckToken(token: String) {
        _tokenLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val offer = checkTokenUseCause.execute(token = token)

                withContext(Dispatchers.Main) {
                    _tokenLiveData.value = ResourceState.Success(offer)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _tokenLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchCheckTokenPreferences(): CheckToken {
        return checkTokenPreferencesUseCause.execute()
    }

    fun fetchLoginTokenPreferences(): Login {
        return checkLoginPreferencesUseCause.execute()
    }

    fun cleanTokenPreferences() {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                cleanTokenPreferences.execute()

            } catch (_: Exception) {

            }
        }
    }

    fun saveTokenPreferences(login: Login) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                saveTokenPreferences.execute(login = login)

            } catch (_: Exception) {

            }
        }
    }
}
