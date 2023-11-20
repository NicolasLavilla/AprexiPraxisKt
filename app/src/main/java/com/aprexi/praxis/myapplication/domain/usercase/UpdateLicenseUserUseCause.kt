package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser

class UpdateLicenseUserUseCause(
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): UpdateLicenseUser {
        return licenseRepository.updateLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }
}