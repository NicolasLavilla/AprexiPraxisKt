package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser

interface ExperienceJobRepository {

    suspend fun getExperienceJobUserList(idUser: Int,forceRemote: Boolean ,token: String): ListExperienceJobUser

    suspend fun getExperienceJobUser(idUser: Int, idExperienceJobUser: Int, token: String): ExperienceJobUser

    fun saveExperienceJobsList(experienceJob: ListExperienceJobUser)

    suspend fun getListExperience(token: String): ListExperience

    fun saveListExperience(experience: ListExperience)

    suspend fun deleteExperienceJobUser(idUser: Int, idExperienceJobUser: Int, token: String): DeleteExperienceJobUser

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
    ): UpdateExperienceJobUser

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
    ): InsertExperienceJobUser
}