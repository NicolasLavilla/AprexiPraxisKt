package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken

class GetCheckTokenUseCause(
    private val tokenRepository: TokenRepository
) {

    suspend fun execute(token: String): CheckToken {
        return tokenRepository.checkToken(token)
    }
}