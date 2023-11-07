package com.aprexi.praxis.myapplication.data.professionalProyects.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser

class ProfessionalProyectsRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getProfessionalProyects(idUser: Int, token: String): ListProfessionalProyectsUser {
        return aprexiPraxisService.getProfessionalProyects(idUser = idUser, token = token)
    }
}