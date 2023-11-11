package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListLevelJob

interface LevelRepository {

    suspend fun getListLevel(token: String): ListLevelJob

    fun saveListLevel(level: ListLevelJob)

}