package com.aprexi.praxis.myapplication.model

data class ListRequestOffer (
    val requestOffer: List<RequestOffer>,
    val success: Boolean,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class RequestOffer (
    val idOffer: Long,
    val idCompany: Long,
    val nameCompany: String,
    val datePublication: String,
    val offerTitle: String,
    val idRequestOffer: Long,
    val dateRequest: String,
    val stateRequest: Long,
    val nameState: String,
    val numRegistered: Long
)