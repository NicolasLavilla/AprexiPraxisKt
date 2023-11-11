package com.aprexi.praxis.myapplication.data.experience

import com.aprexi.praxis.myapplication.data.experience.local.ExperienceJobLocalImpl
import com.aprexi.praxis.myapplication.data.experience.remote.ExperienceJobRetomeImpl
import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser

class ExperienceJobDataImpl(
    private val experienceJobLocalImpl: ExperienceJobLocalImpl,
    private val experienceJobRetomeImpl: ExperienceJobRetomeImpl
) : ExperienceJobRepository {

    override suspend fun getExperienceJobUserList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListExperienceJobUser {
        val cachedExperienceJobUserList = experienceJobLocalImpl.getExperienceJobsList()

        if (cachedExperienceJobUserList != null && forceRemote) {
            return cachedExperienceJobUserList
        } else {
            val remoteExperienceJobUserList =
                experienceJobRetomeImpl.getExperienceJobUserList(idUser = idUser, token = token)
            saveExperienceJobsList(remoteExperienceJobUserList)
            return remoteExperienceJobUserList
        }
    }

    override suspend fun getExperienceJobUser(
        idUser: Int,
        idExperienceJobUser: Int,
        token: String
    ): ExperienceJobUser {
        return experienceJobRetomeImpl.getExperienceJobUser(
            idUser = idUser,
            idExperienceJobUser = idExperienceJobUser,
            token = token
        )
    }

    override fun saveExperienceJobsList(experienceJob: ListExperienceJobUser) {
        experienceJobLocalImpl.saveExpirienceJob(experienceJob)
    }

    override suspend fun getListExperience(
        token: String
    ): ListExperience {

        val cachedExperienceList = experienceJobLocalImpl.getListExperience()

        if (cachedExperienceList != null) {
            return cachedExperienceList
        } else {
            val resultExperience: ListExperience =
                experienceJobRetomeImpl.getListExperience(token = token)
            saveListExperience(resultExperience)
            return resultExperience
        }
    }

    override fun saveListExperience(experience: ListExperience) {
        experienceJobLocalImpl.saveListExperience(experience)
    }

    override suspend fun deleteExperienceJobUser(
        idUser: Int,
        idExperienceJobUser: Int,
        token: String
    ): DeleteExperienceJobUser {
        return experienceJobRetomeImpl.deleteExperienceJobUser(
            idUser = idUser,
            idExperienceJobUser = idExperienceJobUser,
            token = token
        )
    }

    override suspend fun updateExperienceJobUser(
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
        return experienceJobRetomeImpl.updateExperienceJobUser(
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

    override suspend fun insertExperienceJobUser(
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
        return experienceJobRetomeImpl.insertExperienceJobUser(
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