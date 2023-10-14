package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

class GetOffersUseCause(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListOffersResponse {
        return offerRepository.getOffers(idUser , forceRemote,token)
    }
}