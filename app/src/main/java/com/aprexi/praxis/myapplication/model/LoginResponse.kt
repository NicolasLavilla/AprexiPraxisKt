package com.aprexi.praxis.myapplication.model

data class Login (
    val success: Boolean,
    val idUser: Int,
    val token: String
)