package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser

class GetProfessionalProyectsListUseCase(
    private val professionalProyectsRepository: ProfessionalProyectsRepository
) {

    suspend fun execute(idUser: Int ,forceRemote: Boolean = false, token: String): ListProfessionalProyectsUser {
        return professionalProyectsRepository.getProfessionalProyectsList(idUser = idUser, forceRemote = forceRemote, token = token)
    }
}