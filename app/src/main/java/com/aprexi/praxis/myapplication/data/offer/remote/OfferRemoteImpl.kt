package com.aprexi.praxis.myapplication.data.offer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser

class OfferRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getOfferList(idUser: Int, token: String): ListOffersResponse {
        return aprexiPraxisService.getOffersList(idUser = idUser,token = token)
    }

    suspend fun getOffer(idUser: Int,idOffer: Int, token: String): Offer {
        return aprexiPraxisService.getOffer(idUser = idUser, idOffer = idOffer, token = token)
    }

    suspend fun setRequestOffer(idUser: Int ,idOffer: Int, token: String): RequestOfferUser {
        return aprexiPraxisService.setRequestOffer(idUser = idUser, idOffer = idOffer, token = token)
    }

    suspend fun setFollowOffer(idUser: Int ,idOffer: Int, token: String): FollowOfferUser {
        return aprexiPraxisService.setFollowOffer(idUser = idUser, idOffer = idOffer, token = token)
    }

    suspend fun deleteFollowOffer(idUser: Int ,idOffer: Int, token: String): DeleteFollowOfferUser {
        return aprexiPraxisService.deleteFollowOffer(idUser = idUser, idOffer = idOffer, token = token)
    }

    suspend fun getOfferListCompany(idUser: Int, idCompany: Int,token: String): ListOffersResponse {
        return aprexiPraxisService.getOffersListCompany(idUser = idUser,idCompany = idCompany,token = token)
    }
}