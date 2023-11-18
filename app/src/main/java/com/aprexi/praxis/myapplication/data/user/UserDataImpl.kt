package com.aprexi.praxis.myapplication.data.user

import com.aprexi.praxis.myapplication.data.user.local.UserLocalImpl
import com.aprexi.praxis.myapplication.data.user.remote.UserRemoteImpl
import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.CheckEmail
import com.aprexi.praxis.myapplication.model.InsertUser
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User

class UserDataImpl(
    private val userLocalImpl: UserLocalImpl,
    private val userRemoteImpl: UserRemoteImpl
) : UserRepository {

    override suspend fun userData(idUser: Int, token: String): User {

        val userData = userLocalImpl.getUserData()

        if (userData != null) {
            return userData
        } else {

            val response: User = userRemoteImpl.userData(idUser = idUser, token = token)
            saveUserData(response)
            return response
        }
    }

    override suspend fun getCheckEmail(email: String): CheckEmail {
        return userRemoteImpl.getCheckEmail(email = email)
    }

    override fun saveUserData(userData: User) {
        userLocalImpl.saveUserData(userData)
    }

    override suspend fun updateUser(
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
        return userRemoteImpl.updateUser(
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


    override suspend fun insertUser(
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
        return userRemoteImpl.insertUser(
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