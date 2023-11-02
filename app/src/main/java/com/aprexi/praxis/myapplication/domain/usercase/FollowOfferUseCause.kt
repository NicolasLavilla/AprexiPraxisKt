package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.FollowOfferUser

class FollowOfferUseCause(
    private val offerRepository: OfferRepository
) {
    suspend fun execute(idUser: Int, idOffer: Int, token: String): FollowOfferUser {
        return offerRepository.followOffer(idUser,idOffer,token)
    }
}