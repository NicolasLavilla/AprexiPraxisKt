package com.aprexi.praxis.myapplication.data.license

import com.aprexi.praxis.myapplication.data.license.local.LicenseLocalImpl
import com.aprexi.praxis.myapplication.data.license.remote.LicenseRemoteImpl
import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.DeleteLicenseUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser
import com.aprexi.praxis.myapplication.model.LicenseUser
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ListLicenseUser
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser

class LicenseDataImpl (
    private val licenseLocalImpl: LicenseLocalImpl,
    private val licenseRemoteImpl: LicenseRemoteImpl
) : LicenseRepository {

    override suspend fun getListBasicLicense(
        token: String
    ): ListBasicLicense {

        val cachedLicense = licenseLocalImpl.getListBasicLicense()

        if (cachedLicense != null) {
            return cachedLicense
        } else {
            val resultLicense: ListBasicLicense =
                licenseRemoteImpl.getListBasicLicense(token = token)
            saveListBasicLicense(resultLicense)
            return resultLicense
        }
    }

    override fun saveListBasicLicense(license: ListBasicLicense) {
        licenseLocalImpl.saveListBasicLicense(license)
    }

    override suspend fun getLicenseList(
        idUser: Int,
        token: String
    ): ListLicenseUser {
        return licenseRemoteImpl.getLicenseList(
            idUser = idUser,
            token = token
        )
    }

    override suspend fun getLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ): LicenseUser {
        return licenseRemoteImpl.getLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }

    override suspend fun deleteLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ): DeleteLicenseUser {
        return licenseRemoteImpl.deleteLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            token = token
        )
    }

    override suspend fun updateLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): UpdateLicenseUser {
        return licenseRemoteImpl.updateLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }

    override suspend fun insertLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): InsertLicenseUser {
        return licenseRemoteImpl.insertLicenseUser(
            idUser = idUser,
            idLicenseUser = idLicenseUser,
            idLicense = idLicense,
            token = token
        )
    }
}