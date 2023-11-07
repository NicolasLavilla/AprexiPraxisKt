package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListStudiesUser

interface StudiesRepository {

    suspend fun getStudiesList(idUser: Int,forceRemote: Boolean ,token: String): ListStudiesUser

     fun saveStudies(studies: ListStudiesUser)

}