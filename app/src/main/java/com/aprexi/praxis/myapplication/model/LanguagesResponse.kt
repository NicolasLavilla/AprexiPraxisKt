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
    val nameExperience: String
)