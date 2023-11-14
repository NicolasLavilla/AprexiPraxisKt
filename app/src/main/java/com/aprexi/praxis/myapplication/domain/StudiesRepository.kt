package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.ListNameStudies
import com.aprexi.praxis.myapplication.model.ListStudiesUser
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

interface StudiesRepository {

    suspend fun getStudiesList(idUser: Int, forceRemote: Boolean, token: String): ListStudiesUser

    suspend fun getStudiesUser(idUser: Int, idStudiesUser: Int, token: String): StudiesUser

    suspend fun getListTypeStudies(token: String): ListTypeStudies

    suspend fun getListNameStudies(idTypeStudies: Int, idProfessionalFamilies: Int, token: String): ListNameStudies

    fun saveStudies(studies: ListStudiesUser)

    fun saveListTypeStudies(typeStudies: ListTypeStudies)

    suspend fun deleteStudiesUser(idUser: Int, idStudiesUser: Int, token: String): DeleteStudiesUser

    suspend fun updateStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int,
        token: String
    ): UpdateStudiesUser

    suspend fun insertStudiesUser(
        idUser: Int,
        idNameStudies: Int,
        startYear: String,
        endYear: String,
        idSchool: Int,
        idStudiesUser: Int?,
        token: String
    ): InsertStudiesUser

}