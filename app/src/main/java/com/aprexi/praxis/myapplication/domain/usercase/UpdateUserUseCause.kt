package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser
import com.aprexi.praxis.myapplication.model.UpdateUser

class UpdateUserUseCause(
    private val userRepository: UserRepository
) {

    suspend fun execute(
        name: String,
        surname1: String,
        surname2: String,
        gender: Int,
        mobile: Int,
        email: String,
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
    ): UpdateUser {
        return userRepository.updateUser(
            idUser = idUser,
            name = name,
            surname1 = surname1,
            surname2 = surname2,
            gender = gender,
            mobile = mobile,
            email = email,
            dni = dni,
            nie = nie,
            passport = passport,
            birthDate = birthDate,
            address = address,
            municipality = municipality,
            description = description,
            workPermit = workPermit,
            autonomousDischarge = autonomousDischarge,
            ownVehicle = ownVehicle,
            token = token
        )
    }
}