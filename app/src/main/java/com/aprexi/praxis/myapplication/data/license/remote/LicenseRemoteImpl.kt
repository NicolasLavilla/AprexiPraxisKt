package com.aprexi.praxis.myapplication.data.license.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteLicenseUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser
import com.aprexi.praxis.myapplication.model.LicenseUser
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ListLicenseUser
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser

class LicenseRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getListBasicLicense(token: String): ListBasicLicense {
        return aprexiPraxisService.getListBasicLicenses(
            token = token
        )
    }

    suspend fun getLicenseList(idUser: Int, token: String): ListLicenseUser {
        return aprexiPraxisService.getLicenseUserList(idUser = idUser, token = token)
    }

    suspend fun getLicenseUser(idUser: Int, idLicenseUser: Int, token: String): LicenseUser {
        return aprexiPraxisService.getLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }

    suspend fun deleteLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ): DeleteLicenseUser {
        return aprexiPraxisService.deleteLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }

    suspend fun updateLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): UpdateLicenseUser {
        return aprexiPraxisService.updateLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }

    suspend fun insertLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): InsertLicenseUser {
        return aprexiPraxisService.insertLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }

}