package com.aprexi.praxis.myapplication.data.category

import com.aprexi.praxis.myapplication.data.category.local.CategoryLocalImpl
import com.aprexi.praxis.myapplication.data.category.remote.CategoryRemoteImpl
import com.aprexi.praxis.myapplication.domain.CategoryRepository
import com.aprexi.praxis.myapplication.model.ListCategory

class CategoryDataImpl(
    private val categoryLocalImpl: CategoryLocalImpl,
    private val categoryRemoteImpl: CategoryRemoteImpl
) : CategoryRepository {

    override suspend fun getListCategory(
        token: String
    ): ListCategory {

        val cachedCategoryList = categoryLocalImpl.getListCategory()

        if (cachedCategoryList != null) {
            return cachedCategoryList
        } else {
            val resultCategory: ListCategory =
                categoryRemoteImpl.getListCategory(token = token)
            saveListCategory(resultCategory)
            return resultCategory
        }
    }

    override fun saveListCategory(category: ListCategory) {
        categoryLocalImpl.saveListCategory(category)
    }
}