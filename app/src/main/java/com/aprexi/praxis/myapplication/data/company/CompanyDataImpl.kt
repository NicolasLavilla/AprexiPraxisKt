package com.aprexi.praxis.myapplication.data.company

import com.aprexi.praxis.myapplication.data.company.local.CompanyLocalImpl
import com.aprexi.praxis.myapplication.data.company.remote.CompanyRemoteImpl
import com.aprexi.praxis.myapplication.domain.CompanyRepository
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.ListBasicCompany

class CompanyDataImpl(
    private val companyLocalImpl: CompanyLocalImpl,
    private val companyRemoteImpl: CompanyRemoteImpl
) : CompanyRepository {
    override suspend fun getCompany(idUser: Int, idCompany: Int, token: String): Company {
        return companyRemoteImpl.getCompany(idUser = idUser, idCompany = idCompany, token = token)
    }

    override suspend fun getListCompany(
        token: String
    ): ListBasicCompany {

        val cachedStudiesList = companyLocalImpl.getListBasicCompany()

        if (cachedStudiesList != null) {
            return cachedStudiesList
        } else {
            val resultListCompany: ListBasicCompany =
                companyRemoteImpl.getListBasicCompany(token = token)
            saveListCompany(resultListCompany)
            return resultListCompany
        }
    }

    private fun saveListCompany(listCompany: ListBasicCompany) {
        companyLocalImpl.saveListBasicCompany(listCompany)
    }

}