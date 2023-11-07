package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.User

interface UserRepository {

    suspend fun userData(idUser: Int, token: String): User
}