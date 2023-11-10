package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.StudiesUser

class GetExperienceJobUserUseCause (
    private val experienceJobRepository: ExperienceJobRepository
) {

    suspend fun execute(idUser: Int, idExperienceJobUser: Int, token: String): ExperienceJobUser {
        return experienceJobRepository.getExperienceJobUser(idUser = idUser, idExperienceJobUser = idExperienceJobUser, token = token)
    }
}