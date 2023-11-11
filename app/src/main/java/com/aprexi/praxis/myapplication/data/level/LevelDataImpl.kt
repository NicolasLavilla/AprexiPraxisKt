package com.aprexi.praxis.myapplication.data.level

import com.aprexi.praxis.myapplication.data.level.local.LevelLocalImpl
import com.aprexi.praxis.myapplication.data.level.remote.LevelRemoteImpl
import com.aprexi.praxis.myapplication.domain.LevelRepository
import com.aprexi.praxis.myapplication.model.ListLevelJob

class LevelDataImpl(
    private val levelLocalImpl: LevelLocalImpl,
    private val levelRemoteImpl: LevelRemoteImpl
): LevelRepository {

    override suspend fun getListLevel(
        token: String
    ): ListLevelJob {

        val cachedLevel = levelLocalImpl.getListLevel()

        if (cachedLevel != null) {
            return cachedLevel
        } else {
            val resultLevel: ListLevelJob =
                levelRemoteImpl.getListLevel(token = token)
            saveListLevel(resultLevel)
            return resultLevel
        }
    }

    override fun saveListLevel(level: ListLevelJob) {
        levelLocalImpl.saveListLevel(level)
    }
}