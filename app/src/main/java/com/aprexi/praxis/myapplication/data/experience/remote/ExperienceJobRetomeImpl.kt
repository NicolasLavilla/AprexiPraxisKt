package com.aprexi.praxis.myapplication.data.experience.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser

class ExperienceJobRetomeImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun  getExperienceJobUserList(idUser: Int, token: String): ListExperienceJobUser{
        return aprexiPraxisService.getExperienceJobList(idUser = idUser, token = token )
    }
}