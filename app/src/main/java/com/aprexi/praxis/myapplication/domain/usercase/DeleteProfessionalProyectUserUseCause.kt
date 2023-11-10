package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser

class DeleteProfessionalProyectUserUseCause(
    private val professionalProyectsRepository: ProfessionalProyectsRepository
) {

    suspend fun execute(idUser: Int, idProfessionalProyectUser: Int, token: String): DeleteProfessionalProyectsUser {
        return professionalProyectsRepository.deleteProfessionalProyectUser( idUser = idUser, idProfessionalProyectUser = idProfessionalProyectUser, token = token)
    }
}