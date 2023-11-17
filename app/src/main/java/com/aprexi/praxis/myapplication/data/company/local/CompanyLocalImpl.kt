package com.aprexi.praxis.myapplication.data.company.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListBasicCompany

class CompanyLocalImpl (
    private val memoryCache: MemoryCache
) {
    fun getListBasicCompany(): ListBasicCompany?{
        return memoryCache.listCompany
    }
    fun saveListBasicCompany(listCompany: ListBasicCompany){
        memoryCache.listCompany = listCompany
    }

}