package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.CategoryRepository
import com.aprexi.praxis.myapplication.model.ListCategory

class GetListCategoryUseCause (
    private val categoryRepository: CategoryRepository
) {

    suspend fun execute(token: String): ListCategory {
        return categoryRepository.getListCategory( token = token)
    }
}