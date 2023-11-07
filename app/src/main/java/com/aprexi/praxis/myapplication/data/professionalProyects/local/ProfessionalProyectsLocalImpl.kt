package com.aprexi.praxis.myapplication.data.professionalProyects.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListProfessionalProyectsUser

class ProfessionalProyectsLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getProfessionalProyects(): ListProfessionalProyectsUser?{
        return memoryCache.professionalProyectsList
    }
    fun saveProfessionalProyects(professionalProyects: ListProfessionalProyectsUser){
        memoryCache.professionalProyectsList = professionalProyects
    }
}