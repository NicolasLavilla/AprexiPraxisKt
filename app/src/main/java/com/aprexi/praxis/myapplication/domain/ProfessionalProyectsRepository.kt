package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser

interface ProfessionalProyectsRepository {

    suspend fun getProfessionalProyectsList(idUser: Int,forceRemote: Boolean ,token: String): ListProfessionalProyectsUser

     fun saveProfessionalProyects(studies: ListProfessionalProyectsUser)

}