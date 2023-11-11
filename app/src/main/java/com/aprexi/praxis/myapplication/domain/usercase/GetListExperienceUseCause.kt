package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.model.ListExperience

class GetListExperienceUseCause (
    private val experienceRepository: ExperienceJobRepository
) {

    suspend fun execute(token: String): ListExperience {
        return experienceRepository.getListExperience( token = token)
    }
}