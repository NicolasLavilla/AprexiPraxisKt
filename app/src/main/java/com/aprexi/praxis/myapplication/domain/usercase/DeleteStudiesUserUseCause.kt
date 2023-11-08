package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser

class DeleteStudiesUserUseCause(
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute(idUser: Int, idStudiesUser: Int, token: String): DeleteStudiesUser {
        return studiesRepository.deleteStudiesUser( idUser = idUser,idStudiesUser = idStudiesUser, token = token)
    }
}