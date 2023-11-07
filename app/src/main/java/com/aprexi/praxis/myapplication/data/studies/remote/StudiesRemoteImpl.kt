package com.aprexi.praxis.myapplication.data.studies.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListStudiesUser

class StudiesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getStudiesUser(idUser: Int, token: String): ListStudiesUser{
        return aprexiPraxisService.getStudiesList(idUser = idUser, token = token)
    }
}