package com.aprexi.praxis.myapplication.data.experience

import com.aprexi.praxis.myapplication.data.experience.local.ExperienceJobLocalImpl
import com.aprexi.praxis.myapplication.data.experience.remote.ExperienceJobRetomeImpl
import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser

class ExperienceJobDataImpl(
    private val experienceJobLocalImpl: ExperienceJobLocalImpl,
    private val experienceJobRetomeImpl: ExperienceJobRetomeImpl
): ExperienceJobRepository {

    override suspend fun getExperienceJobUserList(idUser: Int, forceRemote: Boolean, token: String): ListExperienceJobUser{
        val cachedExperienceJobUserList = experienceJobLocalImpl.getExperienceJobsList()

        if (cachedExperienceJobUserList != null && forceRemote) {
            return cachedExperienceJobUserList
        } else {
            val remoteExperienceJobUserList = experienceJobRetomeImpl.getExperienceJobUserList(idUser = idUser, token = token)
            saveExperienceJobsList(remoteExperienceJobUserList)
            return remoteExperienceJobUserList
        }
    }

    override fun saveExperienceJobsList(experienceJob: ListExperienceJobUser) {
        experienceJobLocalImpl.saveExpirienceJob(experienceJob)
    }


}