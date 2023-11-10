package com.aprexi.praxis.myapplication.model

data class ListProfessionalProyectsUser (
    val professionalProyectsUser: List<ProfessionalProyectsUser>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class ProfessionalProyectsUser (
    val idProfessionalProyects: Long,
    val nameProyect: String,
    val descriptionProyect: String,
    val websites: String,
    val imageProyect: String? = null,
    val job: String? = null,
    val briefcase: String? = null,
    val initDate: String,
    val endDate: String? = null,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class InsertProfessionalProyectsUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateProfessionalProyectsUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class DeleteProfessionalProyectsUser (
    val success: Boolean = false,
    val idError: String? = null,
    val messageError: String? = null
)
