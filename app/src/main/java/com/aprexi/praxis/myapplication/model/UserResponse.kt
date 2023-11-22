package com.aprexi.praxis.myapplication.model

data class User (
    val idUser: Long,
    val name: String,
    val surname1: String,
    val surname2: String?,
    val idGender: Long,
    val nameGender: String,
    val mobile: Long,
    val email: String,
    val dni: String,
    val nie: String,
    val passport: String,
    val birthDate: String,
    val address: String,
    val idMunicipality: Long,
    val codCountry: Long,
    val nameCountry: String,
    val codAuto: Long,
    val nameCcaa: String,
    val idProvince: Long,
    val nameProvince: String,
    val codMunicipality: Long,
    val nameMunicipality: String,
    val dateCreation: String,
    val description: String,
    val workPermit: Long,
    val autonomousDischarge: Long,
    val ownVehicle: Long,
    val image: String,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)


data class InsertUser (
    val success: Boolean,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class CheckEmail (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)