package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.Offer

class GetOfferUseCause (
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int, idOffer: Int, token: String): Offer {
        return offerRepository.getOffer(idUser = idUser, idOffer = idOffer, token = token)
    }
}