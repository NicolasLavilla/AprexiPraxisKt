package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LevelRepository
import com.aprexi.praxis.myapplication.model.ListLevelJob

class GetListLevelUseCause (
    private val levelRepository: LevelRepository
) {

    suspend fun execute( token: String): ListLevelJob {
        return levelRepository.getListLevel(token = token)
    }
}