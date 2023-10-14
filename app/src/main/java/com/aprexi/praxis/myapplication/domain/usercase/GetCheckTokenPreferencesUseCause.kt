package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken

class GetCheckTokenPreferencesUseCause(
    private val tokenRepository: TokenRepository
) {

    fun execute(): CheckToken {
        return tokenRepository.getCheckTokenPreferences()
    }
}