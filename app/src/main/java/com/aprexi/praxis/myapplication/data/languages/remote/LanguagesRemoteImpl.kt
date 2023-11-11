package com.aprexi.praxis.myapplication.data.languages.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser

class LanguagesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getLanguagesList(idUser: Int, token: String): ListLanguagesUser {
        return aprexiPraxisService.getLanguagesList(idUser = idUser, token = token)
    }

    suspend fun getLanguagesUser(idUser: Int, idLanguages: Int, token: String): LanguagesUser {
        return aprexiPraxisService.getLanguagesUser(
            idUser = idUser,
            idLanguages = idLanguages,
            token = token
        )
    }

    suspend fun getListLanguage(token: String): ListBasicLanguages {
        return aprexiPraxisService.getListLanguages(
            token = token
        )
    }

    suspend fun deleteLanguagesUser(
        idUser: Int,
        idLanguagesUser: Int,
        token: String
    ): DeleteLanguagesUser {
        return aprexiPraxisService.deleteLanguagesUser(
            idUser = idUser,
            idLanguagesUser = idLanguagesUser,
            token = token
        )
    }

    suspend fun updateLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): UpdateLanguagesUser {
        return aprexiPraxisService.updateLanguagesUser(
            idUser = idUser,
            idExperience = idExperience,
            idLanguagesUser = idLanguagesUser,
            idLanguages = idLanguages,
            token = token
        )
    }

    suspend fun insertLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): InsertLanguagesUser {
        return aprexiPraxisService.insertLanguagesUser(
            idUser = idUser,
            idExperience = idExperience,
            idLanguagesUser = idLanguagesUser,
            idLanguages = idLanguages,
            token = token
        )
    }
}