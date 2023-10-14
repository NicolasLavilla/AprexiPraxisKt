package com.aprexi.praxis.myapplication.data.offer.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.Login

class LoginRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getLogin(email: String, password: String): Login {
        return aprexiPraxisService.getLogin(email = email, password = password )
    }



}