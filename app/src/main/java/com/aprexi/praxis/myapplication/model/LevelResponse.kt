package com.aprexi.praxis.myapplication.model

data class ListLevelJob (
    val levelJob: List<LevelJob>,
    val success: Boolean,
    val idError: String? = null,
    val messageError: String? = null
)

data class LevelJob (
    val idLevelJob: Long,
    val nameLevelJob: String,
    val success: Boolean,
    val idError: String?,
    val messageError: String?
)
