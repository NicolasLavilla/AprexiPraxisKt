package com.aprexi.praxis.myapplication.data.category.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListCategory

class CategoryRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getListCategory(token: String): ListCategory {
        return aprexiPraxisService.getListCategory(
            token = token
        )
    }
}