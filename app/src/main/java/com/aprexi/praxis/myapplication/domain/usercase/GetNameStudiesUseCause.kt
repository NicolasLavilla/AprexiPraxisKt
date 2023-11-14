package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ListNameStudies

class GetNameStudiesUseCause (
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute(idTypeStudies: Int, idProfessionalFamilies: Int, token: String): ListNameStudies {
        return studiesRepository.getListNameStudies(idTypeStudies = idTypeStudies, idProfessionalFamilies = idProfessionalFamilies, token = token)
    }
}