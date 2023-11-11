package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ListTypeStudies

class GetListTypeStudiesUseCause (
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute( token: String): ListTypeStudies {
        return studiesRepository.getListTypeStudies(token = token)
    }
}