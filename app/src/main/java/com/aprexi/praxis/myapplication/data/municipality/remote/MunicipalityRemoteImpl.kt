package com.aprexi.praxis.myapplication.data.municipality.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality

class MunicipalityRemoteImpl (
    private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getListBasicMunicipality(): ListBasicMunicipality {
        return aprexiPraxisService.getListBasicMunicipality()
    }
}