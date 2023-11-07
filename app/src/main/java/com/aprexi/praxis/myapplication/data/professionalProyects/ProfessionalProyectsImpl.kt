package com.aprexi.praxis.myapplication.data.professionalProyects

import com.aprexi.praxis.myapplication.data.professionalProyects.local.ProfessionalProyectsLocalImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.remote.ProfessionalProyectsRemoteImpl
import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser

class ProfessionalProyectsImpl(
    private val professionalProyectsLocalImpl: ProfessionalProyectsLocalImpl,
    private val professionalProyectsRemoteImpl: ProfessionalProyectsRemoteImpl
): ProfessionalProyectsRepository {

    override suspend fun getProfessionalProyectsList(idUser: Int, forceRemote: Boolean ,token: String): ListProfessionalProyectsUser {

        val cachedProfessionalProyectsList = professionalProyectsLocalImpl.getProfessionalProyects()

        if (cachedProfessionalProyectsList != null && forceRemote) {
            return cachedProfessionalProyectsList
        } else {
            val resultProfessionalProyects: ListProfessionalProyectsUser = professionalProyectsRemoteImpl.getProfessionalProyects(idUser = idUser, token = token)
            saveProfessionalProyects(resultProfessionalProyects)
            return resultProfessionalProyects
        }
    }

    override fun saveProfessionalProyects(professionalPro: ListProfessionalProyectsUser) {
        professionalProyectsLocalImpl.saveProfessionalProyects(professionalPro)
    }
}