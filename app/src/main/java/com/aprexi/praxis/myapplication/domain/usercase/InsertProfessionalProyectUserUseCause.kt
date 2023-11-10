package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser

class InsertProfessionalProyectUserUseCause(
    private val professionalProyectsRepository: ProfessionalProyectsRepository
) {

    suspend fun execute(
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
        return professionalProyectsRepository.insertProfessionalProyectUser(
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