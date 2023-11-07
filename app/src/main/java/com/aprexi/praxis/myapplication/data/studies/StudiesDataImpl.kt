package com.aprexi.praxis.myapplication.data.studies

import com.aprexi.praxis.myapplication.data.studies.local.StudiesLocalImpl
import com.aprexi.praxis.myapplication.data.studies.remote.StudiesRemoteImpl
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ListStudiesUser

class StudiesDataImpl(
    private val studiesLocalImpl: StudiesLocalImpl,
    private val studiesRemoteImpl: StudiesRemoteImpl
): StudiesRepository {

    override suspend fun getStudiesList(idUser: Int, forceRemote: Boolean ,token: String): ListStudiesUser {

        val cachedStudiesList = studiesLocalImpl.getStudiesUser()

        if (cachedStudiesList != null && forceRemote) {
            return cachedStudiesList
        } else {
            val resultStudies: ListStudiesUser = studiesRemoteImpl.getStudiesUser(idUser = idUser, token = token)
            saveStudies(resultStudies)
            return resultStudies
        }
    }

    override fun saveStudies(studies: ListStudiesUser) {
        studiesLocalImpl.saveStudiesUser(studies)
    }
}