package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.ExperienceJobRepository
import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.StudiesUser

class GetProfessionalProyectsUserUseCause (
    private val professionalProyectsRepository: ProfessionalProyectsRepository
) {

    suspend fun execute(idUser: Int, idProfessionalProyectsUser: Int, token: String): ProfessionalProyectsUser {
        return professionalProyectsRepository.getProfessionalProyectsUser(idUser = idUser, idProfessionalProyectsUser = idProfessionalProyectsUser, token = token)
    }
}