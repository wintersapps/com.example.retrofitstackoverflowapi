package com.example.retrofitstackoverflowapi.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverflowService {

    private const val baseUrl = "https://api.stackexchange.com/"

    private val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StackOverflowApi::class.java)


    fun getQuestions(page: Int): Call<ResponseWrapper<Question>>{
        return api.getQuestions(page)
    }

    fun getAnswers(questionId: Int, page: Int): Call<ResponseWrapper<Answer>>{
        return api.getAnswers(questionId, page)
    }
}