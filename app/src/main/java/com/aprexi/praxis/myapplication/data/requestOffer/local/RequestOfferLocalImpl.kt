package com.aprexi.praxis.myapplication.data.requestOffer.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListRequestOffer

class RequestOfferLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getRequestOffers(): ListRequestOffer? {
        return memoryCache.requestOfferList
    }

    fun saveRequestOffer(requestOffers: ListRequestOffer) {
        memoryCache.requestOfferList = requestOffers
    }
}

