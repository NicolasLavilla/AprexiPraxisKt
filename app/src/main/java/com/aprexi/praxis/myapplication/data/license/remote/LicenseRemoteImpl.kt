package com.aprexi.praxis.myapplication.data.license.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListLicense

class LicenseRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getListLicense(token: String): ListLicense {
        return aprexiPraxisService.getListLicenses(
            token = token
        )
    }
}