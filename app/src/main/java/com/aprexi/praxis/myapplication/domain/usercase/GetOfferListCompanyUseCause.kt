package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.ListOffersResponse

class GetOfferListCompanyUseCause(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int ,idCompany: Int, token: String): ListOffersResponse {
        return offerRepository.getOfferListCompany(idUser = idUser , idCompany = idCompany, token = token)
    }
}