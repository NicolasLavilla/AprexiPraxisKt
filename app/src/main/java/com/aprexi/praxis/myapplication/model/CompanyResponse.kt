package com.aprexi.praxis.myapplication.model

data class Company (
    val idCompany: Long,
    val dateCompanyCreation: String,
    val dateCompanyFundation: String,
    val nameCompany: String,
    val nif: String,
    val address: String,
    val nameMunicipality: String,
    val phone: Long,
    val email: String,
    val websites: String,
    val description: String,
    val numWorkers: Long,
    val nameTypeCompany: String,
    val nameSectorBusiness: String,
    val logoCompany: String,
    val imageCompany: String,
    val followCompany: Long,
    val success: Boolean,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class ListBasicCompany (
    val basicCompany: List<BasicCompany>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class BasicCompany (
    val idCompany: Long,
    val nameCompany: String
)
