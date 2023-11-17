package com.aprexi.praxis.myapplication.data.municipality.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListBasicMunicipality

class MunicipalityLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getListBasicMunicipality(): ListBasicMunicipality?{
        return memoryCache.listMunicipality
    }
    fun saveListBasicMunicipality(listMunicipality: ListBasicMunicipality){
        memoryCache.listMunicipality = listMunicipality
    }
}