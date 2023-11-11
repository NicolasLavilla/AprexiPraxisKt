package com.aprexi.praxis.myapplication.model

data class ListCategory (
    val category: List<Category>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class Category (
    val idCategory: Long,
    val nameCategory: String
)
