package com.aprexi.praxis.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://praxisal.aprexi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}