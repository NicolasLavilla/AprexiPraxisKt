package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.CompanyRepository
import com.aprexi.praxis.myapplication.model.ListBasicCompany

class GetListCompanyUseCause (
    private val companyRepository: CompanyRepository
) {

    suspend fun execute(token: String): ListBasicCompany {
        return companyRepository.getListCompany( token = token)
    }
}