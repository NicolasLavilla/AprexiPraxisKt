package com.aprexi.praxis.myapplication.domain

import android.provider.ContactsContract.CommonDataKinds.Email
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login

interface LoginRepository {

    suspend fun getLogin(email: String, password: String): Login

}