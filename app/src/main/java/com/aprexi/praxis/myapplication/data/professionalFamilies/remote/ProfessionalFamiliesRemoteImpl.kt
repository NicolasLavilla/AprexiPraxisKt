package com.aprexi.praxis.myapplication.data.professionalFamilies.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies

class ProfessionalFamiliesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getListProfessionalFamilias(token: String): ListProfessionalFamilies {
        return aprexiPraxisService.getListProfessionalFamilies(
            token = token
        )
    }

}