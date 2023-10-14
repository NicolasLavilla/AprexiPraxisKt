package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser

class DeleteFollowOfferUseCause(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(idUser: Int, idOffer: Int, token: String): DeleteFollowOfferUser {
        return offerRepository.deleteFollowOffer(idUser,idOffer,token)
    }
}