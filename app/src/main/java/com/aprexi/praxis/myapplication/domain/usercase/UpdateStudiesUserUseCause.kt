package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

class UpdateStudiesUserUseCause(
    private val studiesRepository: StudiesRepository
) {

    suspend fun execute(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int,
        token: String
    ): UpdateStudiesUser {
        return studiesRepository.updateStudiesUser(
            idUser = idUser,
            idNameStudies = idNameStudies,
            startYear = startYear,
            endYear = endYear,
            idSchool = idSchool,
            idStudiesUser = idStudiesUser,
            token = token
        )
    }
}