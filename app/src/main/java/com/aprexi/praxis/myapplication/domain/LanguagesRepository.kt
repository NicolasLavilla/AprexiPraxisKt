package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListLanguagesUser

interface LanguagesRepository {

    suspend fun getLanguagesList(idUser: Int,forceRemote: Boolean ,token: String): ListLanguagesUser

    fun saveLanguagesList(languages: ListLanguagesUser)
}