package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.RequestOfferUser

class RequestOfferUseCause (
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int, idOffer: Int, token:String): RequestOfferUser {
        return offerRepository.setRequestOffer(idUser,idOffer,token)
    }
}