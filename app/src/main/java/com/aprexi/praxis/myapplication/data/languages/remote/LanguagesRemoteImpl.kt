package com.aprexi.praxis.myapplication.data.languages.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListLanguagesUser

class LanguagesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getLanguagesList(idUser: Int, token: String): ListLanguagesUser {
        return aprexiPraxisService.getLanguagesList(idUser = idUser, token = token)
    }

    suspend fun getLanguagesUser(idUser: Int,idLanguages: Int ,token: String): LanguagesUser {
        return aprexiPraxisService.getLanguagesUser(idUser = idUser, idLanguages = idLanguages, token = token)
    }

    suspend fun deleteLanguagesUser(idUser: Int, idLanguagesUser: Int, token: String): DeleteLanguagesUser {
        return aprexiPraxisService.deleteLanguagesUser(idUser = idUser, idLanguagesUser = idLanguagesUser,token = token)
    }
}