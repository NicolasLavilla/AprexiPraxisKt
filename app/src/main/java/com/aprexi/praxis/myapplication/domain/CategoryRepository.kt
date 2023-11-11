package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListCategory

interface CategoryRepository {

    suspend fun getListCategory(token: String): ListCategory

    fun saveListCategory(category: ListCategory)

}