package com.aprexi.praxis.myapplication.model

data class ListBasicMunicipality (
    val municipality: List<BasicMunicipality>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class BasicMunicipality (
    val idMunicipality: Long,
    val nameMunicipality: String
)