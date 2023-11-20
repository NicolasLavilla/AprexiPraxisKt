package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser

class InsertLicenseUserUseCause(
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): InsertLicenseUser {
        return licenseRepository.insertLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }
}