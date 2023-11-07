package com.aprexi.praxis.myapplication.data.user.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.User

class UserRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun userData( idUser: Int, token: String): User {
        return aprexiPraxisService.getUserData(idUser = idUser, token = token)
    }
}