package com.aprexi.praxis.myapplication.data.experience.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListExperienceJobUser

class ExperienceJobLocalImpl(
    private val memoryCache: MemoryCache
) {

    fun getExperienceJobsList(): ListExperienceJobUser?{
        return memoryCache.experienceJobUserList
    }

    fun saveExpirienceJob(experieceJobUser : ListExperienceJobUser){
        memoryCache.experienceJobUserList = experieceJobUser
    }
}