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
    val startYear: String,
    val endYear: String
)
