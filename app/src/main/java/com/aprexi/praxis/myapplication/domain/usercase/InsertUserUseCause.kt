package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.InsertUser

class InsertUserUseCause(
    private val userRepository: UserRepository
) {

    suspend fun execute(
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
    ): InsertUser {
        return userRepository.insertUser(
            name = name,
            surname1 = surname1,
            surname2 = surname2,
            gender = gender,
            mobile = mobile,
            email = email,
            password = password,
            dni = dni,
            nie = nie,
            passport = passport,
            birthDate = birthDate,
            address = address,
            municipality = municipality,
            description = description,
            workPermit = workPermit,
            autonomousDischarge = autonomousDischarge,
            ownVehicle = ownVehicle
        )
    }
}