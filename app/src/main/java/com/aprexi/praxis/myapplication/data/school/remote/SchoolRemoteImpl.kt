package com.aprexi.praxis.myapplication.data.school.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.ListSchool

class SchoolRemoteImpl (
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getListSchool(token: String): ListSchool {
        return aprexiPraxisService.getListSchools(
            token = token
        )
    }
}