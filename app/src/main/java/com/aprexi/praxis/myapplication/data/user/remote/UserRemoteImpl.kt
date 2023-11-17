package com.aprexi.praxis.myapplication.data.user.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User

class UserRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun userData( idUser: Int, token: String): User {
        return aprexiPraxisService.getUserData(idUser = idUser, token = token)
    }

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
    ): UpdateUser {
        return aprexiPraxisService.updateUser(
            idUser = idUser,
            name = name,
            surname1 = surname1,
            surname2 = surname2,
            gender = gender,
            mobile = mobile,
            //email = email,
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