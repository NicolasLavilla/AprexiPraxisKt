package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.FollowOfferRepository
import com.aprexi.praxis.myapplication.domain.OfferRepository
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

class GetFollowOffersUseCause(
    private val followOfferRepository: FollowOfferRepository
) {
    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListOffersResponse {
        return followOfferRepository.getListFollowOffer(idUser , forceRemote,token)
    }
}