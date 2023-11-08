package com.aprexi.praxis.myapplication.data.studies

import com.aprexi.praxis.myapplication.data.studies.local.StudiesLocalImpl
import com.aprexi.praxis.myapplication.data.studies.remote.StudiesRemoteImpl
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.StudiesUser

class StudiesDataImpl(
    private val studiesLocalImpl: StudiesLocalImpl,
    private val studiesRemoteImpl: StudiesRemoteImpl
): StudiesRepository {

    override suspend fun getStudiesList(idUser: Int, forceRemote: Boolean ,token: String): ListStudiesUser {

        val cachedStudiesList = studiesLocalImpl.getStudiesUser()

        if (cachedStudiesList != null && forceRemote) {
            return cachedStudiesList
        } else {
            val resultStudies: ListStudiesUser = studiesRemoteImpl.getStudiesUserList(idUser = idUser, token = token)
            saveStudies(resultStudies)
            return resultStudies
        }
    }

    override suspend fun getStudiesUser(idUser: Int, idStudiesUser: Int, token: String): StudiesUser {
        return studiesRemoteImpl.getStudiesUser(idUser = idUser,idStudiesUser = idStudiesUser, token = token)
    }

    override fun saveStudies(studies: ListStudiesUser) {
        studiesLocalImpl.saveStudiesUser(studies)
    }

    override suspend fun deleteStudiesUser(idUser: Int, idStudiesUser: Int, token: String): DeleteStudiesUser {
        return studiesRemoteImpl.deleteStudiesUser(idUser = idUser,idStudiesUser = idStudiesUser, token = token )
    }
}