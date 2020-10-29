package com.example.retrofitstackoverflowapi.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("/2.2/questions?order=desc&sort=votes&tagged=android&site=stackoverflow")
    fun getQuestions(@Query("page") page: Int): Call<ResponseWrapper<Question>>
}