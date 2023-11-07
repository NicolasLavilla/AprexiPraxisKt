package com.aprexi.praxis.myapplication.data.user

import com.aprexi.praxis.myapplication.data.user.local.UserLocalImpl
import com.aprexi.praxis.myapplication.data.user.remote.UserRemoteImpl
import com.aprexi.praxis.myapplication.domain.UserRepository
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
}