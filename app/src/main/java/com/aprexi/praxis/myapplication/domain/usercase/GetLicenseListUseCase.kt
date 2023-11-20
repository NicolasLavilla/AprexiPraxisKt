package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.ListLicenseUser

class GetLicenseListUseCase(
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(idUser: Int, token: String): ListLicenseUser {
        return licenseRepository.getLicenseList(idUser = idUser, token = token)
    }
}