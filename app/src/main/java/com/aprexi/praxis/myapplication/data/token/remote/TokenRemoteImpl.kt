package com.aprexi.praxis.myapplication.data.offer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.CheckToken

class TokenRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun checkToken(token: String): CheckToken{
        return aprexiPraxisService.getCheckToken(token = token)
    }

}