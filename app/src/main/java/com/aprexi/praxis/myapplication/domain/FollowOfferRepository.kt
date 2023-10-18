package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

interface FollowOfferRepository {

    suspend fun getListFollowOffer(idUser: Int, forceRemote: Boolean, token: String): ListOffersResponse
    fun saveFollowOffers(followOffer: ListOffersResponse)
}