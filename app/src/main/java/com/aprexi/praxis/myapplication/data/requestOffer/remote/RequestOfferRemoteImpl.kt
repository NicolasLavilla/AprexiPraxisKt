package com.aprexi.praxis.myapplication.data.requestOffer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer

class RequestOfferRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getRequestOfferList(idUser: Int, token: String): ListRequestOffer {
        return aprexiPraxisService.getRequestOfferList(idUser = idUser, token = token)
    }
}