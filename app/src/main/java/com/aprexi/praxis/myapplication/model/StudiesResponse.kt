package com.aprexi.praxis.myapplication.model

data class ListStudiesUser (
    val studiesUser: List<StudiesUser>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class StudiesUser (
    val idStudiesUser: Long,
    val idNameStudies: Long,
    val nameStudies: String,
    val typeStudies: Long,
    val nameTypeStudies: String,
    val professionalFamilies: Long,
    val nameFamily: String,
    val startYear: String,
    val endYear: String,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class DeleteStudiesUser (
    val success: Boolean = false,
    val idError: String? = null,
    val messageError: String? = null
)