package com.aprexi.praxis.myapplication.data.studies

import com.aprexi.praxis.myapplication.data.studies.local.StudiesLocalImpl
import com.aprexi.praxis.myapplication.data.studies.remote.StudiesRemoteImpl
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.ListNameStudies
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

class StudiesDataImpl(
    private val studiesLocalImpl: StudiesLocalImpl,
    private val studiesRemoteImpl: StudiesRemoteImpl
) : StudiesRepository {

    override suspend fun getStudiesList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListStudiesUser {

        val cachedStudiesList = studiesLocalImpl.getStudiesUser()

        if (cachedStudiesList != null && forceRemote) {
            return cachedStudiesList
        } else {
            val resultStudies: ListStudiesUser =
                studiesRemoteImpl.getStudiesUserList(idUser = idUser, token = token)
            saveStudies(resultStudies)
            return resultStudies
        }
    }

    override suspend fun getStudiesUser(
        idUser: Int,
        idStudiesUser: Int,
        token: String
    ): StudiesUser {
        return studiesRemoteImpl.getStudiesUser(
            idUser = idUser,
            idStudiesUser = idStudiesUser,
            token = token
        )
    }

    override suspend fun getListTypeStudies(
        token: String
    ): ListTypeStudies {

        val cachedStudiesList = studiesLocalImpl.getListTypeStudies()

        if (cachedStudiesList != null) {
            return cachedStudiesList
        } else {
            val resultTypeStudies: ListTypeStudies =
                studiesRemoteImpl.getListTypeStudies(token = token)
            saveListTypeStudies(resultTypeStudies)
            return resultTypeStudies
        }
    }

    override fun saveListTypeStudies(typeStudies: ListTypeStudies) {
        studiesLocalImpl.saveListTypeStudies(typeStudies)
    }

    override suspend fun getListNameStudies(
        idTypeStudies: Int,
        idProfessionalFamilies: Int,
        token: String
    ): ListNameStudies {
        return studiesRemoteImpl.getListNameStudies(
            idTypeStudies = idTypeStudies,
            idProfessionalFamilies = idProfessionalFamilies,
            token = token
        )
    }

    override fun saveStudies(studies: ListStudiesUser) {
        studiesLocalImpl.saveStudiesUser(studies)
    }

    override suspend fun deleteStudiesUser(
        idUser: Int,
        idStudiesUser: Int,
        token: String
    ): DeleteStudiesUser {
        return studiesRemoteImpl.deleteStudiesUser(
            idUser = idUser,
            idStudiesUser = idStudiesUser,
            token = token
        )
    }

    override suspend fun updateStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int,
        token: String
    ): UpdateStudiesUser {
        return studiesRemoteImpl.updateStudiesUser(
            idUser = idUser,
            idNameStudies = idNameStudies,
            startYear = startYear,
            endYear = endYear,
            idSchool = idSchool,
            idStudiesUser = idStudiesUser,
            token = token
        )
    }

    override suspend fun insertStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int?,
        token: String
    ): InsertStudiesUser {
        return studiesRemoteImpl.insertStudiesUser(
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