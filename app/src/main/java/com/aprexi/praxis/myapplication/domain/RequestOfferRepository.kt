package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer

interface RequestOfferRepository {

    suspend fun getRequestOfferList(idUser: Int, forceRemote: Boolean, token: String): ListRequestOffer

    fun saveRequestOffers(requestOffers: ListRequestOffer)

}