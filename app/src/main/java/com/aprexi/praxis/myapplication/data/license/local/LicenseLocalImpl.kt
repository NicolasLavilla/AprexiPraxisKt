package com.aprexi.praxis.myapplication.data.license.local

import com.aprexi.praxis.myapplication.data.local.MemoryCache
import com.aprexi.praxis.myapplication.model.ListLicense

class LicenseLocalImpl(
    private val memoryCache: MemoryCache
) {

    fun getListLicense(): ListLicense?{
        return memoryCache.license
    }
    fun saveListLicense(license: ListLicense){
        memoryCache.license = license
    }
}