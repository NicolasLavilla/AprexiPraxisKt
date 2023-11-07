package com.aprexi.praxis.myapplication.data.studies.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListStudiesUser

class StudiesLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getStudiesUser(): ListStudiesUser?{
        return memoryCache.studiesUserList
    }
    fun saveStudiesUser(studies: ListStudiesUser){
        memoryCache.studiesUserList = studies
    }
}