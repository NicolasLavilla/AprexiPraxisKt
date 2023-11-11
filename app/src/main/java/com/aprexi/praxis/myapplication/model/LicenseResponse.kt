package com.aprexi.praxis.myapplication.model

data class ListLicense (
    val license: List<License>,
    val success: Boolean,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class License (
    val idLicense: Long,
    val nameLicense: String
)
