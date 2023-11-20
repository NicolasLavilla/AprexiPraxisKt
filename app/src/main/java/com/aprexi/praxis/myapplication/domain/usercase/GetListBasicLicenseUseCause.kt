package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.ListBasicLicense

class GetListBasicLicenseUseCause (
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(token: String): ListBasicLicense {
        return licenseRepository.getListBasicLicense( token = token)
    }
}