package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.ListBasicCompany

interface CompanyRepository {

    suspend fun getCompany(idUser: Int, idCompany: Int, token: String): Company

    suspend fun getListCompany(token: String): ListBasicCompany
}