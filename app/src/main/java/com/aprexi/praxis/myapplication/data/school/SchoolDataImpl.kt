package com.aprexi.praxis.myapplication.data.school

import com.aprexi.praxis.myapplication.data.school.local.SchoolLocalImpl
import com.aprexi.praxis.myapplication.data.school.remote.SchoolRemoteImpl
import com.aprexi.praxis.myapplication.domain.SchoolRepository
import com.aprexi.praxis.myapplication.model.ListSchool

class SchoolDataImpl(
    private val schoolLocalImpl: SchoolLocalImpl,
    private val schoolRemoteImpl: SchoolRemoteImpl
) : SchoolRepository {

    override suspend fun getListSchool(
        token: String
    ): ListSchool {

        val cachedStudiesList = schoolLocalImpl.getListSchool()

        if (cachedStudiesList != null) {
            return cachedStudiesList
        } else {
            val resultSchool: ListSchool =
                schoolRemoteImpl.getListSchool( token = token)
            saveListSchool(resultSchool)
            return resultSchool
        }
    }

    override fun saveListSchool(school: ListSchool) {
        schoolLocalImpl.saveListSchool(school)
    }
}