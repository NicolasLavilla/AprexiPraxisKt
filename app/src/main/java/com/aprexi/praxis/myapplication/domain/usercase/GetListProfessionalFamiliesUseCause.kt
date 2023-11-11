package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ProfessionalFamiliesRepository
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies

class GetListProfessionalFamiliesUseCause (
    private val professionalFamiliesRepository: ProfessionalFamiliesRepository
) {

    suspend fun execute(token: String): ListProfessionalFamilies {
        return professionalFamiliesRepository.getListProfessionalFamilies(token = token)
    }
}