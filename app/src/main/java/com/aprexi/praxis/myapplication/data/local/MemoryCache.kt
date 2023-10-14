package com.aprexi.praxis.myapplication.data.local

import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.Offer

class MemoryCache {

    var offerList: ListOffersResponse? = null

    fun clearAll() {
        offerList = null
    }
}