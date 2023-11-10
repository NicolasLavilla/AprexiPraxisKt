package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser

interface LanguagesRepository {

    suspend fun getLanguagesList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListLanguagesUser

    suspend fun getLanguagesUser(idUser: Int, idLanguages: Int, token: String): LanguagesUser

    fun saveLanguagesList(languages: ListLanguagesUser)

    suspend fun deleteLanguagesUser(
        idUser: Int,
        idLanguagesUser: Int,
        token: String
    ): DeleteLanguagesUser

    suspend fun updateLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): UpdateLanguagesUser

    suspend fun insertLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): InsertLanguagesUser

}