package com.aprexi.praxis.myapplication.data.studies.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.StudiesUser

class StudiesRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getStudiesUserList(idUser: Int, token: String): ListStudiesUser{
        return aprexiPraxisService.getStudiesList(idUser = idUser, token = token)
    }

    suspend fun getStudiesUser(idUser: Int,idStudiesUser: Int, token: String): StudiesUser {
        return aprexiPraxisService.getStudiesUser(idUser = idUser, idStudiesUser = idStudiesUser,token = token)
    }

    suspend fun deleteStudiesUser(idUser: Int, idStudiesUser: Int, token: String): DeleteStudiesUser {
        return aprexiPraxisService.deleteStudiesUser(idUser = idUser, idStudiesUser = idStudiesUser,token = token)
    }
}