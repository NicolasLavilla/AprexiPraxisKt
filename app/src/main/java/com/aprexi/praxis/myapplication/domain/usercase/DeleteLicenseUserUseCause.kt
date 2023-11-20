package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.DeleteLicenseUser

class DeleteLicenseUserUseCause(
    private val licenseRepository: LicenseRepository
) {

    suspend fun execute(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ): DeleteLicenseUser {
        return licenseRepository.deleteLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }
}