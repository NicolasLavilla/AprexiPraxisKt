package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.CheckEmail

class GetCheckEmailUseCause(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String): CheckEmail {
        return userRepository.getCheckEmail(email)
    }
}