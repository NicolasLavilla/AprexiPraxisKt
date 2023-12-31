package com.aprexi.praxis.myapplication.data.languages.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListLanguagesUser

class LanguagesLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getLanguagesList(): ListLanguagesUser?{
        return memoryCache.languagesUserList
    }

    fun saveLanguagesList(languages: ListLanguagesUser){
        memoryCache.languagesUserList = languages
    }

    fun getListLanguagesBasic(): ListBasicLanguages?{
        return memoryCache.languagesBasic
    }
    fun saveListLanguagesBasic(languages: ListBasicLanguages){
        memoryCache.languagesBasic = languages
    }
}