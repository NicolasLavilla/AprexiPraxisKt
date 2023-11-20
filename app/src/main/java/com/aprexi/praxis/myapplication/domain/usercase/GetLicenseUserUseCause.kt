package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.LicenseUser

class GetLicenseUserUseCause(
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(idUser: Int, idLicenseUser: Int, token: String): LicenseUser {
        return licenseRepository.getLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }
}