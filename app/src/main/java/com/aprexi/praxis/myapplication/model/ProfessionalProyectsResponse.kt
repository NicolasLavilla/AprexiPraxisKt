package com.aprexi.praxis.myapplication.model

data class ListProfessionalProyectsUser (
    val professionalProyectsUser: List<ProfessionalProyectsUser>,
    val success: Boolean,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class ProfessionalProyectsUser (
    val idProfessionalProyects: Long,
    val nameProyect: String,
    val descriptionProyect: String,
    val websites: String,
    val imageProyect: Any? = null,
    val job: Any? = null,
    val briefcase: Any? = null,
    val initDate: String,
    val endDate: Any? = null
)
