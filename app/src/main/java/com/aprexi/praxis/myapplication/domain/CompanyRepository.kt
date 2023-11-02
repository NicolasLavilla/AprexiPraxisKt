package com.aprexi.praxis.myapplication.domain

import com.aprexi.praxis.myapplication.model.Company

interface CompanyRepository {

    suspend fun getCompany(idUser: Int, idCompany: Int, token: String): Company
}