package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListSchool

interface SchoolRepository {

    suspend fun getListSchool(token: String): ListSchool

    fun saveListSchool(school: ListSchool)


}