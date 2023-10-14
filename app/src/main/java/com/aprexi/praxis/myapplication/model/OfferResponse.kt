package com.aprexi.praxis.myapplication.model

data class ListOffersResponse (
    val offer: List<Offer>,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class Offer (
    val idOffer: Long,
    val idCompany: Long,
    val nameCompany: String,
    val nameModality: String,
    val datePublication: String,
    val nameWorking: String,
    val nameContract: String,
    val numVacancies: Long,
    val nameTypeStudies: String,
    val nameStudies: String,
    val nameSkills: String,
    val minRequirements: String,
    val jobDescription: String,
    val numRegistered: Long,
    val nameMunicipality: String,
    val salary: Long,
    val offerTitle: String,
    val minExperience: Long,
    val followCompany: Long,
    val followOffer: Long,
    val requestOffer: Long,
    val combinedLanguages: String, //Es la unica de su tipo que tiene que tener valor.
    val combinedLicenses: String?,
    val logoCompany: String?,
    val imageCompany: String?,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)

data class RequestOfferUser (
    val success: Boolean = false,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class FollowOfferUser (
    val success: Boolean = false,
    val idError: Any? = null,
    val messageError: Any? = null
)

data class DeleteFollowOfferUser (
    val success: Boolean = false,
    val idError: Any? = null,
    val messageError: Any? = null
)


