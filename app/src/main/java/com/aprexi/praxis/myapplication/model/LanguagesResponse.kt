package com.aprexi.praxis.myapplication.model

data class ListLanguagesUser (
    val languagesUser: List<LanguagesUser>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class LanguagesUser (
    val idLanguages: Long,
    val nameLanguages: String,
    val idExperience: Long,
    val nameExperience: String,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class InsertLanguagesUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateLanguagesUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class DeleteLanguagesUser (
    val success: Boolean = false,
    val idError: String? = null,
    val messageError: String? = null
)