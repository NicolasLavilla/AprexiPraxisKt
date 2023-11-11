package com.aprexi.praxis.myapplication.data.level.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListLevelJob

class LevelLocalImpl(
    private val memoryCache: MemoryCache
) {

    fun getListLevel(): ListLevelJob?{
        return memoryCache.level
    }
    fun saveListLevel(level: ListLevelJob){
        memoryCache.level = level
    }
}