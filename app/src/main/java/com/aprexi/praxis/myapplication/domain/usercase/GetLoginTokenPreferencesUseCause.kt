package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login

class GetLoginTokenPreferencesUseCause(
    private val tokenRepository: TokenRepository
) {

    fun execute(): Login {
        return tokenRepository.getLoginTokenPreferences()
    }
}