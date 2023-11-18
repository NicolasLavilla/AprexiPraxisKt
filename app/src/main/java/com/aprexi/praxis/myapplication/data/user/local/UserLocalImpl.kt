package com.aprexi.praxis.myapplication.data.user.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.User

class UserLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getUserData(): User?{
        return memoryCache.userData
    }
    fun saveUserData(userData: User){
        memoryCache.userData = userData
    }
}