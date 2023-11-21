package com.aprexi.praxis.myapplication.data.offer.local

import android.content.Context
import android.content.SharedPreferences
import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.PreferencesKeys

class TokenLocalImpl(
    private val context: Context,
    private val memoryCache: MemoryCache
) {
    private lateinit var preferences: SharedPreferences

    fun getCheckTokenPreferences(): CheckToken {
        preferences = context.getSharedPreferences(PreferencesKeys.PREF_KEY, Context.MODE_PRIVATE)

        val checkToken: String? = preferences.getString(PreferencesKeys.LOGIN_KEY, null)
        val message: String? = preferences.getString(PreferencesKeys.MESSAGE_KEY, null)

        return  CheckToken(checkToken.toBoolean(), message.orEmpty())
    }

    fun getLoginTokenPreferences(): Login {
        preferences = context.getSharedPreferences(PreferencesKeys.PREF_KEY, Context.MODE_PRIVATE)

        val success: String? = preferences.getString(PreferencesKeys.LOGIN_KEY, null)
        val idUser: String? = preferences.getString(PreferencesKeys.ID_USER, "0")
        val token: String? = preferences.getString(PreferencesKeys.TOKEN_KEY, null)

        if ((idUser != null) &&  (token != null) && (success != null)) {
            return  Login(success.toBoolean(), idUser.toInt(), token)
        }else{
            return  Login(success.toBoolean(), 0, token.orEmpty())
        }
    }

    fun saveTokenPreferences(login: Login) {
        preferences = context.getSharedPreferences(PreferencesKeys.PREF_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .putString(PreferencesKeys.LOGIN_KEY, login.success.toString())
            .putString(PreferencesKeys.ID_USER, login.idUser.toString())
            .putString(PreferencesKeys.TOKEN_KEY, login.token)
            .apply()
    }

    fun cleanTokenPreferences(){
        preferences = context.getSharedPreferences(PreferencesKeys.PREF_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .putString(PreferencesKeys.LOGIN_KEY, "false")
            .putString(PreferencesKeys.MESSAGE_KEY, "")
            .putString(PreferencesKeys.ID_USER, "0")
            .putString(PreferencesKeys.TOKEN_KEY, "false")
            .apply()

        memoryCache.clearAll()
    }
}