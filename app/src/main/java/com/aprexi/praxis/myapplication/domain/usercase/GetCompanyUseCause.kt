package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.CompanyRepository
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.FollowOfferUser

class GetCompanyUseCause (
    private val companyRepository: CompanyRepository
) {

    suspend fun execute(idUser: Int, idCompany: Int, token: String): Company {
        return companyRepository.getCompany(idUser = idUser, idCompany = idCompany, token = token)
    }
}