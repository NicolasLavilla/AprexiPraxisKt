package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser

class DeleteLanguagesUserUseCause(
    private val languagesRepository: LanguagesRepository
) {

    suspend fun execute(idUser: Int, idLanguagesUser: Int, token: String): DeleteLanguagesUser {
        return languagesRepository.deleteLanguagesUser( idUser = idUser,idLanguagesUser = idLanguagesUser, token = token)
    }
}