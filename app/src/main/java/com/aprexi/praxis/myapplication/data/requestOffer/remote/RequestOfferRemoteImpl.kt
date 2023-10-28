package com.aprexi.praxis.myapplication.data.requestOffer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer

class RequestOfferRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getRequestOfferList(idUser: Int, token: String): ListRequestOffer {
        return aprexiPraxisService.getRequestOfferList(idUser = idUser, token = token)
    }

    suspend fun getDetailRequestOfferList(idUser: Int, idOffer: Int, token: String): ListDetailRequestOffer {
        return aprexiPraxisService.getDetailRequestOfferList(idUser = idUser, idOffer = idOffer ,token = token)
    }
}