package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login

interface TokenRepository {

    suspend fun checkToken(token: String): CheckToken

    fun cleanTokenPreferences()

    fun getCheckTokenPreferences():CheckToken

    fun getLoginTokenPreferences():Login

    fun saveTokenPreferences(login: Login)

}