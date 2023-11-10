package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

class InsertExperienceJobUserUseCause(
    private val experienceJobRepository: ExperienceJobRepository
) {

    suspend fun execute(
        idUser: Int,
        nameJobs: String,
        level: Int,
        category: Int,
        descriptionJob: String,
        idCompany: Int,
        initDate: String,
        endDate: String,
        idExperienceJobUser: Int,
        token: String
    ): InsertExperienceJobUser {
        return experienceJobRepository.insertExperienceJobUser(
            idUser = idUser,
            nameJobs = nameJobs,
            level = level,
            category = category,
            descriptionJob = descriptionJob,
            idCompany = idCompany,
            initDate = initDate,
            endDate = endDate,
            idExperienceJobUser = idExperienceJobUser,
            token = token
        )
    }
}