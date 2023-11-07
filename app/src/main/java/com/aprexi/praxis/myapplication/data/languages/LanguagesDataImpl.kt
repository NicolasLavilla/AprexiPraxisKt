package com.aprexi.praxis.myapplication.data.languages

import com.aprexi.praxis.myapplication.data.languages.local.LanguagesLocalImpl
import com.aprexi.praxis.myapplication.data.languages.remote.LanguagesRemoteImpl
import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.ListLanguagesUser

class LanguagesDataImpl(
    private val languagesLocalImpl: LanguagesLocalImpl,
    private val languagesRemoteImpl: LanguagesRemoteImpl
): LanguagesRepository {
    override suspend fun getLanguagesList(idUser: Int, forceRemote: Boolean, token: String): ListLanguagesUser {
        val cachedStudiesList = languagesLocalImpl.getLanguagesList()

        if (cachedStudiesList != null && forceRemote) {
            return cachedStudiesList
        } else {
            val remoteLanguagesList = languagesRemoteImpl.getLanguagesList(idUser = idUser, token = token)
            saveLanguagesList(remoteLanguagesList)
            return remoteLanguagesList
        }
    }

    override fun saveLanguagesList(languages: ListLanguagesUser) {
        languagesLocalImpl.saveLanguagesList(languages)
    }

}