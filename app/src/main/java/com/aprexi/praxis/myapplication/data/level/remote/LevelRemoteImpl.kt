package com.aprexi.praxis.myapplication.data.level.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListLevelJob

class LevelRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {
    suspend fun getListLevel(token: String): ListLevelJob {
        return aprexiPraxisService.getListLevel(
            token = token
        )
    }
}