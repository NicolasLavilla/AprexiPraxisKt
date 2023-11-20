package com.aprexi.praxis.myapplication.data.license.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListBasicLicense

class LicenseLocalImpl(
    private val memoryCache: MemoryCache
) {

    fun getListBasicLicense(): ListBasicLicense?{
        return memoryCache.listBasicLicense
    }
    fun saveListBasicLicense(license: ListBasicLicense){
        memoryCache.listBasicLicense = license
    }
}