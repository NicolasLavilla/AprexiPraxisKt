package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser

interface ProfessionalProyectsRepository {

    suspend fun getProfessionalProyectsList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListProfessionalProyectsUser

    suspend fun getProfessionalProyectsUser(
        idUser: Int,
        idProfessionalProyectsUser: Int,
        token: String
    ): ProfessionalProyectsUser

    fun saveProfessionalProyects(studies: ListProfessionalProyectsUser)

    suspend fun deleteProfessionalProyectUser(
        idUser: Int,
        idProfessionalProyectUser: Int,
        token: String
    ): DeleteProfessionalProyectsUser

    suspend fun updateProfessionalProyectUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ): UpdateProfessionalProyectsUser

    suspend fun insertProfessionalProyectUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ): InsertProfessionalProyectsUser


}