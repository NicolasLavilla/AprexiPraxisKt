package com.aprexi.praxis.myapplication.data.token

import com.aprexi.praxis.myapplication.data.offer.local.TokenLocalImpl
import com.aprexi.praxis.myapplication.data.token.remote.TokenRemoteImpl
import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login

class TokenDataImpl (
    private val tokenLocalImpl: TokenLocalImpl,
    private val tokenRemoteImpl: TokenRemoteImpl
) : TokenRepository {

    override suspend fun checkToken(token: String): CheckToken {

        val resultToken: CheckToken = tokenRemoteImpl.checkToken(token)

        if (resultToken.checkToken.equals(false) || resultToken.checkToken.equals("")) {
            cleanTokenPreferences()
            return resultToken
        }else{
            return resultToken
        }
    }

    override fun getCheckTokenPreferences(): CheckToken {
        return tokenLocalImpl.getCheckTokenPreferences()
    }

    override fun getLoginTokenPreferences(): Login {
        return tokenLocalImpl.getLoginTokenPreferences()
    }

    override fun saveTokenPreferences(login : Login) {
        tokenLocalImpl.saveTokenPreferences(login = login)
    }

    override fun cleanTokenPreferences(){
        tokenLocalImpl.cleanTokenPreferences()
    }
}