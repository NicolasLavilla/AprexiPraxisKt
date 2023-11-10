package com.aprexi.praxis.myapplication.model

data class ListExperienceJobUser (
    val experienceJobUser: List<ExperienceJobUser>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class ExperienceJobUser (
    val idExperienceJobUser: Long,
    val nameJobs: String,
    val level: Long,
    val nameLevelJob: String,
    val idCategory: Long,
    val nameCategory: String,
    val descriptionJob: String,
    val idCompany: Long,
    val nameCompany: String,
    val initDate: String,
    val endDate: String,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class InsertExperienceJobUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateExperienceJobUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class DeleteExperienceJobUser (
    val success: Boolean = false,
    val idError: String? = null,
    val messageError: String? = null
)