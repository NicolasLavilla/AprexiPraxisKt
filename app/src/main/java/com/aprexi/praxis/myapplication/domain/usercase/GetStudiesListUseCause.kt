package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ListStudiesUser

class GetStudiesListUseCause(
    private val studiesRepository: StudiesRepository
) {
    suspend fun execute(idUser: Int, forceRemote: Boolean = false, token: String): ListStudiesUser{
        return studiesRepository.getStudiesList(idUser = idUser, forceRemote = forceRemote, token = token)
    }
}