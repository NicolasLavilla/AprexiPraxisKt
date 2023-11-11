package com.aprexi.praxis.myapplication.data.professionalFamilies

import com.aprexi.praxis.myapplication.data.professionalFamilies.local.ProfessionalFamiliesLocalImpl
import com.aprexi.praxis.myapplication.data.professionalFamilies.remote.ProfessionalFamiliesRemoteImpl
import com.aprexi.praxis.myapplication.domain.ProfessionalFamiliesRepository
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies

class ProfessionalFamiliesDataImpl(
    private val professionalFamiliesLocalImpl: ProfessionalFamiliesLocalImpl,
    private val professionalFamiliesRemoteImpl: ProfessionalFamiliesRemoteImpl
) : ProfessionalFamiliesRepository {

    override suspend fun getListProfessionalFamilies(
        token: String
    ): ListProfessionalFamilies {

        val cachedProfessionalFamiliesList = professionalFamiliesLocalImpl.getListProfessionalFamilies()

        if (cachedProfessionalFamiliesList != null) {
            return cachedProfessionalFamiliesList
        } else {
            val resultProfessionalFamilies: ListProfessionalFamilies =
                professionalFamiliesRemoteImpl.getListProfessionalFamilias( token = token)
            saveListProfessionalFamilies(resultProfessionalFamilies)
            return resultProfessionalFamilies
        }
    }

    override fun saveListProfessionalFamilies(professionalFamilies: ListProfessionalFamilies) {
        professionalFamiliesLocalImpl.saveListProfessionalFamilies(professionalFamilies)
    }
}