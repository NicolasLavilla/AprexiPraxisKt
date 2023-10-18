package com.aprexi.praxis.myapplication.data.followOffer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

class FollowOfferRemoteImpl(
private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getListFollowOffer(idUser: Int, token: String): ListOffersResponse {
        return aprexiPraxisService.getFollowOfferList(idUser = idUser,token = token)
    }

    suspend fun getOffer(idUser: Int,idOffer: Int, token: String): Offer {
        return aprexiPraxisService.getOffer(idUser = idUser, idOffer = idOffer, token = token)
    }
}