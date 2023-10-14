package com.aprexi.praxis.myapplication.data.login

import android.content.Context
import android.content.SharedPreferences
import com.aprexi.praxis.myapplication.data.login.local.LoginLocalImpl
import com.aprexi.praxis.myapplication.data.offer.remote.LoginRemoteImpl
import com.aprexi.praxis.myapplication.domain.LoginRepository
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.PreferencesKeys

class LoginDataImpl (
    private val loginLocalImpl: LoginLocalImpl,
    private val loginRemoteImpl: LoginRemoteImpl
) : LoginRepository {


    override suspend fun getLogin(email: String, password: String): Login {
            return loginRemoteImpl.getLogin(email = email, password = password)
    }

}