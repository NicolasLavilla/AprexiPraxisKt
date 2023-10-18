package com.aprexi.praxis.myapplication.data.local

import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.Offer

class MemoryCache {

    var offerList: ListOffersResponse? = null
    var followOfferList: ListOffersResponse? = null
    var RequestOfferList: ListRequestOffer? = null

    fun clearAll() {
        offerList = null
        RequestOfferList = null
        followOfferList= null
    }

}