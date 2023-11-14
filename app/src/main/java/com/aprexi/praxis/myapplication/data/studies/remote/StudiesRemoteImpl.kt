package com.aprexi.praxis.myapplication.data.studies.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.ListNameStudies
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

class StudiesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getStudiesUserList(idUser: Int, token: String): ListStudiesUser {
        return aprexiPraxisService.getStudiesList(idUser = idUser, token = token)
    }

    suspend fun getStudiesUser(idUser: Int, idStudiesUser: Int, token: String): StudiesUser {
        return aprexiPraxisService.getStudiesUser(
            idUser = idUser, idStudiesUser = idStudiesUser, token = token
        )
    }

    suspend fun getListTypeStudies(token: String): ListTypeStudies {
        return aprexiPraxisService.getListTypeStudies(
            token = token
        )
    }

    suspend fun getListNameStudies(idTypeStudies: Int, idProfessionalFamilies: Int, token: String): ListNameStudies {
        return aprexiPraxisService.getListNameStudies(
            idTypeStudies = idTypeStudies,
            idProfessionalFamilies = idProfessionalFamilies,
            token = token
        )
    }

    suspend fun deleteStudiesUser(
        idUser: Int, idStudiesUser: Int, token: String
    ): DeleteStudiesUser {
        return aprexiPraxisService.deleteStudiesUser(
            idUser = idUser, idStudiesUser = idStudiesUser, token = token
        )
    }

    suspend fun updateStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int,
        token: String
    ): UpdateStudiesUser {
        return aprexiPraxisService.updateStudiesUser(
            idUser = idUser,
            idNameStudies = idNameStudies,
            startYear = startYear,
            endYear = endYear,
            idSchool = idSchool,
            idStudiesUser = idStudiesUser,
            token = token
        )
    }

    suspend fun insertStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int?,
        token: String
    ): InsertStudiesUser {
        return aprexiPraxisService.insertStudiesUser(
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