package com.aprexi.praxis.myapplication.model

data class ListExperience (
    val experience: List<Experience>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class Experience (
    val idExperience: Long,
    val nameExperience: String
)
