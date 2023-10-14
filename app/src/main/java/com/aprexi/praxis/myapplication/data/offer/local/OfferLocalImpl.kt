package com.aprexi.praxis.myapplication.data.offer.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Offer

class OfferLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getOffers(): ListOffersResponse? {
        return memoryCache.offerList
    }

    fun saveOffer(offer: ListOffersResponse) {
        memoryCache.offerList = offer
    }
}