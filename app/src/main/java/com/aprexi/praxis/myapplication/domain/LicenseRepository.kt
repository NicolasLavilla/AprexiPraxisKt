package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListLicense

interface LicenseRepository {

    suspend fun getListLicense(token: String): ListLicense

    fun saveListLicense(school: ListLicense)


}