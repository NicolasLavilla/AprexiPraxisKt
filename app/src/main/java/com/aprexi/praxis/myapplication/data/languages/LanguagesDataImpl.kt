package com.aprexi.praxis.myapplication.data.languages

import com.aprexi.praxis.myapplication.data.languages.local.LanguagesLocalImpl
import com.aprexi.praxis.myapplication.data.languages.remote.LanguagesRemoteImpl
import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser

class LanguagesDataImpl(
    private val languagesLocalImpl: LanguagesLocalImpl,
    private val languagesRemoteImpl: LanguagesRemoteImpl
) : LanguagesRepository {
    override suspend fun getLanguagesList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListLanguagesUser {
        val cachedStudiesList = languagesLocalImpl.getLanguagesList()

        if (cachedStudiesList != null && forceRemote) {
            return cachedStudiesList
        } else {
            val remoteLanguagesList =
                languagesRemoteImpl.getLanguagesList(idUser = idUser, token = token)
            saveLanguagesList(remoteLanguagesList)
            return remoteLanguagesList
        }
    }

    override suspend fun getLanguagesUser(
        idUser: Int,
        idLanguages: Int,
        token: String
    ): LanguagesUser {
        return languagesRemoteImpl.getLanguagesUser(
            idUser = idUser,
            idLanguages = idLanguages,
            token = token
        )
    }

    override fun saveLanguagesList(languages: ListLanguagesUser) {
        languagesLocalImpl.saveLanguagesList(languages)
    }

    override suspend fun getListLanguagesBasic(
        token: String
    ): ListBasicLanguages {

        val cachedLanguages = languagesLocalImpl.getListLanguagesBasic()

        if (cachedLanguages != null) {
            return cachedLanguages
        } else {
            val resultLanguagesBasic: ListBasicLanguages =
                languagesRemoteImpl.getListBasicLanguages(token = token)
            saveListLanguagesBasic(resultLanguagesBasic)
            return resultLanguagesBasic
        }
    }

    override fun saveListLanguagesBasic(languages: ListBasicLanguages) {
        languagesLocalImpl.saveListLanguagesBasic(languages)
    }

    override suspend fun deleteLanguagesUser(
        idUser: Int,
        idLanguagesUser: Int,
        token: String
    ): DeleteLanguagesUser {
        return languagesRemoteImpl.deleteLanguagesUser(
            idUser = idUser,
            idLanguagesUser = idLanguagesUser,
            token = token
        )
    }

    override suspend fun updateLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): UpdateLanguagesUser {
        return languagesRemoteImpl.updateLanguagesUser(
            idUser = idUser,
            idExperience = idExperience,
            idLanguagesUser = idLanguagesUser,
            idLanguages = idLanguages,
            token = token
        )
    }

    override suspend fun insertLanguagesUser(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): InsertLanguagesUser {
        return languagesRemoteImpl.insertLanguagesUser(
            idUser = idUser,
            idExperience = idExperience,
            idLanguagesUser = idLanguagesUser,
            idLanguages = idLanguages,
            token = token
        )
    }

}