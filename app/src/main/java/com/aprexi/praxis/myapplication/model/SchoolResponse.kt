package com.aprexi.praxis.myapplication.model

data class ListSchool (
    val school: List<School>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class School (
    val idSchool: Long,
    val nameSchool: String
)
