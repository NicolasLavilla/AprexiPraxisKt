package com.aprexi.praxis.myapplication.data.experience.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser

class ExperienceJobRetomeImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getExperienceJobUserList(idUser: Int, token: String): ListExperienceJobUser {
        return aprexiPraxisService.getExperienceJobList(idUser = idUser, token = token)
    }

    suspend fun getListExperience(token: String): ListExperience {
        return aprexiPraxisService.getListExperience(
            token = token
        )
    }

    suspend fun getExperienceJobUser(
        idUser: Int,
        idExperienceJobUser: Int,
        token: String
    ): ExperienceJobUser {
        return aprexiPraxisService.getExperienceJobUser(
            idUser = idUser,
            idExperienceJobUser = idExperienceJobUser,
            token = token
        )
    }

    suspend fun deleteExperienceJobUser(
        idUser: Int,
        idExperienceJobUser: Int,
        token: String
    ): DeleteExperienceJobUser {
        return aprexiPraxisService.deleteExperienceJobUser(
            idUser = idUser,
            idExperienceJobUser = idExperienceJobUser,
            token = token
        )
    }

    suspend fun updateExperienceJobUser(
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
    ): UpdateExperienceJobUser {
        return aprexiPraxisService.updateExperienceJobUser(
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

    suspend fun insertExperienceJobUser(
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
        return aprexiPraxisService.insertExperienceJobUser(
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