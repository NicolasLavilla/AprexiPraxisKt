package com.aprexi.praxis.myapplication.data.professionalProyects.remote

import com.aprexi.praxis.myapplication.data.remote.AprexiPraxisService
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser

class ProfessionalProyectsRemoteImpl(
    private val aprexiPraxisService: AprexiPraxisService
) {

    suspend fun getProfessionalProyectsList(
        idUser: Int,
        token: String
    ): ListProfessionalProyectsUser {
        return aprexiPraxisService.getProfessionalProyectsList(idUser = idUser, token = token)
    }

    suspend fun getProfessionalProyectsUser(
        idUser: Int,
        idProfessionalProyectsUser: Int,
        token: String
    ): ProfessionalProyectsUser {
        return aprexiPraxisService.getProfessionalProyectsUser(
            idUser = idUser,
            idProfessionalProyectsUser = idProfessionalProyectsUser,
            token = token
        )
    }

    suspend fun deleteProfessionalProyectsUser(
        idUser: Int,
        idProfessionalProyectUser: Int,
        token: String
    ): DeleteProfessionalProyectsUser {
        return aprexiPraxisService.deleteProfessionalProyectsJobUser(
            idUser = idUser,
            idProfessionalProyectUser = idProfessionalProyectUser,
            token = token
        )
    }

    suspend fun updateProfessionalProyectsUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ): UpdateProfessionalProyectsUser {
        return aprexiPraxisService.updateProfessionalProyectsUser(
            idUser = idUser,
            nameProyect = nameProyect,
            descriptionProyect = descriptionProyect,
            websites = websites,
            job = job,
            initDate = initDate,
            endDate = endDate,
            idProfessionalProyectUser = idProfessionalProyectUser,
            token = token
        )
    }

    suspend fun insertProfessionalProyectsUser(
        idUser: Int,
        nameProyect: String,
        descriptionProyect: String,
        websites: String,
        job: String,
        initDate: String,
        endDate: String,
        idProfessionalProyectUser: Int,
        token: String
    ): InsertProfessionalProyectsUser {
        return aprexiPraxisService.insertProfessionalProyectsUser(
            idUser = idUser,
            nameProyect = nameProyect,
            descriptionProyect = descriptionProyect,
            websites = websites,
            job = job,
            initDate = initDate,
            endDate = endDate,
            idProfessionalProyectUser = idProfessionalProyectUser,
            token = token
        )
    }
}