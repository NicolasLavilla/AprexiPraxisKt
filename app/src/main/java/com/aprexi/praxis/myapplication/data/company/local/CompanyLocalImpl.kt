package com.aprexi.praxis.myapplication.data.company.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListBasicCompany

class CompanyLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getListCompany(): ListBasicCompany?{
        return memoryCache.listCompany
    }
    fun saveListCompany(listCompany: ListBasicCompany){
        memoryCache.listCompany = listCompany
    }

}