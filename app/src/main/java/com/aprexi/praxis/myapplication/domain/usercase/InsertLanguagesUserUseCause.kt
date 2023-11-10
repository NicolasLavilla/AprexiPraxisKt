package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.domain.StudiesRepository
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser
import com.aprexi.praxis.myapplication.model.UpdateProfessionalProyectsUser
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser

class InsertLanguagesUserUseCause(
    private val languagesRepository: LanguagesRepository
) {

    suspend fun execute(
        idUser: Int,
        idExperience: Int,
        idLanguagesUser: Int,
        idLanguages: Int,
        token: String
    ): InsertLanguagesUser {
        return languagesRepository.insertLanguagesUser(
            idUser = idUser,
            idExperience = idExperience,
            idLanguagesUser = idLanguagesUser,
            idLanguages = idLanguages,
            token = token
        )
    }
}