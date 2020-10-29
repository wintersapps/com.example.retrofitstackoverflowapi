package com.example.retrofitstackoverflowapi.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StackOverflowService {

    private val baseUrl = "https://api.stackexchange.com/"

    private val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StackOverflowApi::class.java)


}