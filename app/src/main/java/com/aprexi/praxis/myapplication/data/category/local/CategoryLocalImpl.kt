package com.aprexi.praxis.myapplication.data.category.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListCategory

class CategoryLocalImpl(
    private val memoryCache: MemoryCache
) {

    fun getListCategory(): ListCategory?{
        return memoryCache.category
    }
    fun saveListCategory(category: ListCategory){
        memoryCache.category = category
    }
}