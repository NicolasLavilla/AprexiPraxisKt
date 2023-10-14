package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken

class CleanTokenPreferencesUseCause(
    private val tokenRepository: TokenRepository
) {

    suspend fun execute() {
        tokenRepository.cleanTokenPreferences()
    }
}