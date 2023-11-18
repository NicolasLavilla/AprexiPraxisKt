package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.CheckEmail
import com.aprexi.praxis.myapplication.model.InsertUser
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User

interface UserRepository {

    suspend fun userData(idUser: Int, token: String): User

    fun saveUserData(userData: User)

     suspend fun getCheckEmail(email: String): CheckEmail

    suspend fun updateUser(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
        //email: String,
        dni: String,
        nie: String,
        passport: String,
        birthDate: String,
        address: String,
        municipality: Int,
        description: String,
        workPermit: Int,
        autonomousDischarge: Int,
        ownVehicle: Int,
        idUser: Int,
        token: String
    ): UpdateUser

    suspend fun insertUser(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
        email: String,
        password: String,
        dni: String,
        nie: String,
        passport: String,
        birthDate: String,
        address: String,
        municipality: Int,
        description: String,
        workPermit: Int,
        autonomousDischarge: Int,
        ownVehicle: Int
    ): InsertUser
}