package com.aprexi.praxis.myapplication.data.professionalProyects

import com.aprexi.praxis.myapplication.data.professionalProyects.local.ProfessionalProyectsLocalImpl
import com.aprexi.praxis.myapplication.data.professionalProyects.remote.ProfessionalProyectsRemoteImpl
import com.aprexi.praxis.myapplication.domain.ProfessionalProyectsRepository
import com.aprexi.praxis.myapplication.model.DeleteProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.InsertProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.ProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser

class ProfessionalProyectsImpl(
    private val professionalProyectsLocalImpl: ProfessionalProyectsLocalImpl,
    private val professionalProyectsRemoteImpl: ProfessionalProyectsRemoteImpl
) : ProfessionalProyectsRepository {

    override suspend fun getProfessionalProyectsList(
        idUser: Int,
        forceRemote: Boolean,
        token: String
    ): ListProfessionalProyectsUser {

        val cachedProfessionalProyectsList = professionalProyectsLocalImpl.getProfessionalProyects()

        if (cachedProfessionalProyectsList != null && forceRemote) {
            return cachedProfessionalProyectsList
        } else {
            val resultProfessionalProyects: ListProfessionalProyectsUser =
                professionalProyectsRemoteImpl.getProfessionalProyectsList(
                    idUser = idUser,
                    token = token
                )
            saveProfessionalProyects(resultProfessionalProyects)
            return resultProfessionalProyects
        }
    }

    override suspend fun getProfessionalProyectsUser(
        idUser: Int,
        idProfessionalProyectsUser: Int,
        token: String
    ): ProfessionalProyectsUser {
        return professionalProyectsRemoteImpl.getProfessionalProyectsUser(
            idUser = idUser,
            idProfessionalProyectsUser = idProfessionalProyectsUser,
            token = token
        )
    }

    override fun saveProfessionalProyects(professionalPro: ListProfessionalProyectsUser) {
        professionalProyectsLocalImpl.saveProfessionalProyects(professionalPro)
    }

    override suspend fun deleteProfessionalProyectUser(
        idUser: Int,
        idProfessionalProyectUser: Int,
        token: String
    ): DeleteProfessionalProyectsUser {
        return professionalProyectsRemoteImpl.deleteProfessionalProyectsUser(
            idUser = idUser,
            idProfessionalProyectUser = idProfessionalProyectUser,
            token = token
        )
    }

    override suspend fun updateProfessionalProyectUser(
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
        return professionalProyectsRemoteImpl.updateProfessionalProyectsUser(
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

    override suspend fun insertProfessionalProyectUser(
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
        return professionalProyectsRemoteImpl.insertProfessionalProyectsUser(
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