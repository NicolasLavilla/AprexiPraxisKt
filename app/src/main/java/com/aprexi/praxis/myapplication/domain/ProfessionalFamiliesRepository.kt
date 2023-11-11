package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies

interface ProfessionalFamiliesRepository {

    suspend fun getListProfessionalFamilies(token: String): ListProfessionalFamilies

    fun saveListProfessionalFamilies(professionalFamilies: ListProfessionalFamilies)


}