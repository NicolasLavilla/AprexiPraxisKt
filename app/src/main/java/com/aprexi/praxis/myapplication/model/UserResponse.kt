package com.aprexi.praxis.myapplication.model

data class RegisterUser (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class CheckEmail (
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)
