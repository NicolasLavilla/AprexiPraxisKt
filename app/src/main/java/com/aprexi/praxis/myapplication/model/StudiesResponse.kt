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
    val idSchool: Long,
    val nameSchool: String,
    val startYear: String,
    val endYear: String,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class InsertStudiesUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class UpdateStudiesUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class DeleteStudiesUser (
    val success: Boolean = false,
    val idError: String? = null,
    val messageError: String? = null
)

data class ListTypeStudies (
    val typeStudies: List<TypeStudy>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class TypeStudy (
    val idTypeStudies: Long,
    val nameTypeStudies: String
)

data class NameStudies (
    val idNameStudies: Long,
    val nameStudies: String,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)


