package com.aprexi.praxis.myapplication.model

data class ListBasicLicense (
    val license: List<BasicLicense>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class BasicLicense (
    val idLicense: Long,
    val nameLicense: String
)


data class ListLicenseUser (
    val licenseUser: List<LicenseUser>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class LicenseUser (
    val idLicenseUser: Long,
    val idLicense: Long,
    val nameLicense: String,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)


data class InsertLicenseUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateLicenseUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class DeleteLicenseUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)
