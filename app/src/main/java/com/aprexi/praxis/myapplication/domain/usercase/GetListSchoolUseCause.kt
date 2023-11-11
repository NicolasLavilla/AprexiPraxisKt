package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.SchoolRepository
import com.aprexi.praxis.myapplication.model.ListSchool

class GetListSchoolUseCause (
    private val schoolRepository: SchoolRepository
) {

    suspend fun execute(token: String): ListSchool {
        return schoolRepository.getListSchool(token = token)
    }
}