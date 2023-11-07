package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.User

class GetUserDataUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(idUser: Int, token: String): User {
        return userRepository.userData(idUser = idUser, token = token)
    }
}