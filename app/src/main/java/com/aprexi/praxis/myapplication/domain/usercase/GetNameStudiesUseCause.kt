package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.NameStudies

class GetNameStudiesUseCause (
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute(idTypeStudies: Int, idProfessionalFamilies: Int, token: String): NameStudies {
        return studiesRepository.getNameStudies(idTypeStudies = idTypeStudies, idProfessionalFamilies = idProfessionalFamilies, token = token)
    }
}