package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LoginRepository
import com.aprexi.praxis.myapplication.domain.TokenRepository
import com.aprexi.praxis.myapplication.model.CheckToken
import com.aprexi.praxis.myapplication.model.Login

class GetLoginUseCause(
    private val loginRepository: LoginRepository
) {

    suspend fun execute(email: String, password: String ): Login {
        return loginRepository.getLogin(email, password)
    }
}