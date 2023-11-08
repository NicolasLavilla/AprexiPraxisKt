package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.LanguagesUser

class GetLanguagesUserUseCause (
    private val languagesRepository: LanguagesRepository
) {

    suspend fun execute(idUser: Int, idLanguages: Int, token: String): LanguagesUser {
        return languagesRepository.getLanguagesUser(idUser = idUser, idLanguages = idLanguages, token = token)
    }
}