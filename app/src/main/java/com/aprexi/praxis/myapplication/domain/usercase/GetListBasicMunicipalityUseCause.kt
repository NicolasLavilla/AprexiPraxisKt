package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.MunicipalityRepository
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality

class GetListBasicMunicipalityUseCause (
    private val municipalityRepository: MunicipalityRepository
) {

    suspend fun execute(): ListBasicMunicipality {
        return municipalityRepository.getListBasicMunicipality()
    }
}