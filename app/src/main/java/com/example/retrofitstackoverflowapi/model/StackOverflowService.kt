package com.example.retrofitstackoverflowapi.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverflowService {

    private const val baseUrl = "https://api.stackexchange.com/"

    val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StackOverflowApi::class.java)

}