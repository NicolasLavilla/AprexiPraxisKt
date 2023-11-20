package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteLicenseUser
import com.aprexi.praxis.myapplication.model.InsertLicenseUser
import com.aprexi.praxis.myapplication.model.LicenseUser
import com.aprexi.praxis.myapplication.model.ListBasicLicense
import com.aprexi.praxis.myapplication.model.ListLicenseUser
import com.aprexi.praxis.myapplication.model.UpdateLicenseUser

interface LicenseRepository {

    suspend fun getListBasicLicense(token: String): ListBasicLicense

    fun saveListBasicLicense(license: ListBasicLicense)

    suspend fun getLicenseUser(idUser: Int, idLicenseUser: Int, token: String): LicenseUser

    suspend fun getLicenseList(
        idUser: Int,
        token: String
    ): ListLicenseUser

    suspend fun deleteLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        token: String
    ): DeleteLicenseUser

    suspend fun updateLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): UpdateLicenseUser

    suspend fun insertLicenseUser(
        idUser: Int,
        idLicenseUser: Int,
        idLicense: Int,
        token: String
    ): InsertLicenseUser

}