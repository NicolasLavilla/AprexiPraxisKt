package com.aprexi.praxis.myapplication.data.user

import com.aprexi.praxis.myapplication.data.user.local.UserLocalImpl
import com.aprexi.praxis.myapplication.data.user.remote.UserRemoteImpl
import com.aprexi.praxis.myapplication.domain.UserRepository
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateUser
import com.aprexi.praxis.myapplication.model.User

class UserDataImpl(
    private val userLocalImpl: UserLocalImpl,
    private val userRemoteImpl: UserRemoteImpl
): UserRepository {

    override suspend fun userData(idUser: Int ,token: String): User {

       // val resutUserData: User = userRemoteImpl.userData(idUser = idUser, token = token)

        return userRemoteImpl.userData(idUser = idUser, token = token)

        /* if (resutUserData.checkToken.equals(false) || resutUserData.checkToken.equals("")) {
             cleanTokenPreferences()
             return resultToken
         }else{
             return resultToken
         }*/
    }

    override suspend fun updateUser(
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
        return userRemoteImpl.updateUser(
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