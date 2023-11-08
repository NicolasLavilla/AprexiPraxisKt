package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.StudiesUser

class GetStudiesUserUseCause (
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute(idUser: Int, idStudiesUser: Int, token: String): StudiesUser {
        return studiesRepository.getStudiesUser(idUser = idUser, idStudiesUser = idStudiesUser, token = token)
    }
}