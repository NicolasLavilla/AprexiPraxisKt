package com.aprexi.praxis.myapplication.data.municipality

import com.aprexi.praxis.myapplication.data.municipality.local.MunicipalityLocalImpl
import com.aprexi.praxis.myapplication.data.municipality.remote.MunicipalityRemoteImpl
import com.aprexi.praxis.myapplication.domain.MunicipalityRepository
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality

class MunicipalityDataImpl(
    private val municipalityLocalImpl: MunicipalityLocalImpl,
    private val municipalityRemoteImpl: MunicipalityRemoteImpl
) : MunicipalityRepository {


    override suspend fun getListBasicMunicipality(
        token: String
    ): ListBasicMunicipality {

        val cachedMunicipalityList = municipalityLocalImpl.getListBasicMunicipality()

        if (cachedMunicipalityList != null) {
            return cachedMunicipalityList
        } else {
            val resultListCompany: ListBasicMunicipality =
                municipalityRemoteImpl.getListBasicMunicipality(token = token)
            saveListBasicMunicipality(resultListCompany)
            return resultListCompany
        }
    }

    private fun saveListBasicMunicipality(listMunicipality: ListBasicMunicipality) {
        municipalityLocalImpl.saveListBasicMunicipality(listMunicipality)
    }
}