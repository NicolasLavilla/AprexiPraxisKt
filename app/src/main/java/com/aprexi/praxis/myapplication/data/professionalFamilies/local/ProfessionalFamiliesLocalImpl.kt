package com.aprexi.praxis.myapplication.data.professionalFamilies.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies

class ProfessionalFamiliesLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getListProfessionalFamilies(): ListProfessionalFamilies?{
        return memoryCache.professionalFamilies
    }
    fun saveListProfessionalFamilies(professionalFamilies: ListProfessionalFamilies) {
        memoryCache.professionalFamilies = professionalFamilies
    }
}