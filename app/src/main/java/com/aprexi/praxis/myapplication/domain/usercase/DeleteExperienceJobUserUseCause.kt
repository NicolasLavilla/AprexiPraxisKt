package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser

class DeleteExperienceJobUserUseCause(
    private val experienceJobUserRepository: ExperienceJobRepository
) {

    suspend fun execute(idUser: Int, idExperienceJobUser: Int, token: String): DeleteExperienceJobUser {
        return experienceJobUserRepository.deleteExperienceJobUser( idUser = idUser, idExperienceJobUser = idExperienceJobUser, token = token)
    }
}