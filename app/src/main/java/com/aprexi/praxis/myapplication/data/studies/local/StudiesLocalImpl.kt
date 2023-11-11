package com.aprexi.praxis.myapplication.data.studies.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies

class StudiesLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getStudiesUser(): ListStudiesUser?{
        return memoryCache.studiesUserList
    }
    fun saveStudiesUser(studies: ListStudiesUser){
        memoryCache.studiesUserList = studies
    }

    fun getListTypeStudies(): ListTypeStudies?{
        return memoryCache.typeStudies
    }
    fun saveListTypeStudies(studies: ListTypeStudies){
        memoryCache.typeStudies = studies
    }
}