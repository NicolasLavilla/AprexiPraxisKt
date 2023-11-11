package com.aprexi.praxis.myapplication.data.license

import com.aprexi.praxis.myapplication.data.license.local.LicenseLocalImpl
import com.aprexi.praxis.myapplication.data.license.remote.LicenseRemoteImpl
import com.aprexi.praxis.myapplication.domain.LicenseRepository
import com.aprexi.praxis.myapplication.model.ListLicense

class LicenseDataImpl (
    private val licenseLocalImpl: LicenseLocalImpl,
    private val licenseRemoteImpl: LicenseRemoteImpl
) : LicenseRepository {

    override suspend fun getListLicense(
        token: String
    ): ListLicense {

        val cachedLicense = licenseLocalImpl.getListLicense()

        if (cachedLicense != null) {
            return cachedLicense
        } else {
            val resultLicense: ListLicense =
                licenseRemoteImpl.getListLicense(token = token)
            saveListLicense(resultLicense)
            return resultLicense
        }
    }

    override fun saveListLicense(license: ListLicense) {
        licenseLocalImpl.saveListLicense(license)
    }
}