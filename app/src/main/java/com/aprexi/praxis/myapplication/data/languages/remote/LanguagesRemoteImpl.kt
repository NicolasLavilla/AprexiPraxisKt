package com.aprexi.praxis.myapplication.data.languages.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListLanguagesUser

class LanguagesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getLanguagesList(idUser: Int, token: String): ListLanguagesUser {
        return aprexiPraxisService.getLanguagesList(idUser = idUser, token = token)
    }
}