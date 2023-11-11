package com.aprexi.praxis.myapplication.domain.usercase

import com.aprexi.praxis.myapplication.domain.LanguagesRepository
import com.aprexi.praxis.myapplication.model.ListBasicLanguages

class GetListLanguagesBasicUseCause (
    private val languagesRepository: LanguagesRepository
) {

    suspend fun execute(token: String): ListBasicLanguages {
        return languagesRepository.getListLanguagesBasic( token = token)
    }
}