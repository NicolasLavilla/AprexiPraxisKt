package com.aprexi.praxis.myapplication.data.school.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListSchool

class SchoolLocalImpl (
    private val memoryCache: MemoryCache
) {

    fun getListSchool(): ListSchool?{
        return memoryCache.school
    }
    fun saveListSchool(school: ListSchool){
        memoryCache.school = school
    }
}