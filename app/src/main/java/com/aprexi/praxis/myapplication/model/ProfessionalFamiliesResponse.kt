package com.aprexi.praxis.myapplication.model

data class ListProfessionalFamilies (
    val professionalFamilies: List<ProfessionalFamily>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class ProfessionalFamily (
    val idProfessionalFamilies: Long,
    val nameFamily: String
)
