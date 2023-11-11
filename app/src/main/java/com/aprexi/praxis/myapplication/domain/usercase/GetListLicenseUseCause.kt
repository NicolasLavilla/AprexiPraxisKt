package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.ListLicense

class GetListLicenseUseCause (
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(token: String): ListLicense {
        return licenseRepository.getListLicense( token = token)
    }
}