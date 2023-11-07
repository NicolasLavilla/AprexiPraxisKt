package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListExperienceJobUser

interface ExperienceJobRepository {

    suspend fun getExperienceJobUserList(idUser: Int,forceRemote: Boolean ,token: String): ListExperienceJobUser

    fun saveExperienceJobsList(experienceJob: ListExperienceJobUser)
}