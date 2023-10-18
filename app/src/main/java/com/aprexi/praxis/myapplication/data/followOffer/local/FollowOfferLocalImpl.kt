package com.aprexi.praxis.myapplication.data.followOffer.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListOffersResponse

class FollowOfferLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getFollowOffers(): ListOffersResponse? {
        return memoryCache.followOfferList
    }

    fun saveFollowOffer(followOffer: ListOffersResponse) {
        memoryCache.followOfferList = followOffer
    }
}