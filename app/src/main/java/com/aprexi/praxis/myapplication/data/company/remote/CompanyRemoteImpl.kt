package com.aprexi.praxis.myapplication.data.company.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.ListBasicCompany

class CompanyRemoteImpl (
private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getCompany(idUser: Int,idCompany: Int, token: String): Company {
        return aprexiPraxisService.getCompany(idUser = idUser, idCompany = idCompany, token = token)
    }

    suspend fun getListBasicCompany(token: String): ListBasicCompany {
        return aprexiPraxisService.getListBasicCompany(token = token)
    }

}