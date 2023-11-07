package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.ListLanguagesUser

class GetLanguagesListUseCase(
    private val languagesRepository: LanguagesRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListLanguagesUser {
        return languagesRepository.getLanguagesList(idUser = idUser, forceRemote = forceRemote, token = token)
    }
}