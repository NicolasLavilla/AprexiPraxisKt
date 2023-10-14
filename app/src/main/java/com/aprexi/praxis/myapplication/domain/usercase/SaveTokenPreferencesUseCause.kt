package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.Login

class SaveTokenPreferencesUseCause(
    private val tokenRepository: TokenRepository
) {

    suspend fun execute(login : Login) {
        tokenRepository.saveTokenPreferences(login)
    }
}