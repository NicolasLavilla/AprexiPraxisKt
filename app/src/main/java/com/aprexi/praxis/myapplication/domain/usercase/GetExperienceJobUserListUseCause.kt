package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser

class GetExperienceJobUserListUseCause(
    private val experienceJobRepository: ExperienceJobRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListExperienceJobUser{
        return experienceJobRepository.getExperienceJobUserList(idUser = idUser, forceRemote = forceRemote, token = token)
    }
}