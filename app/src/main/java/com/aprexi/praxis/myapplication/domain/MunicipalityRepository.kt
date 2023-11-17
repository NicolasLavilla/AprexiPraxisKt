package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListBasicMunicipality

interface MunicipalityRepository {

    suspend fun getListBasicMunicipality(token: String): ListBasicMunicipality
}