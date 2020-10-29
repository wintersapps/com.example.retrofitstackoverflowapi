package com.example.retrofitstackoverflowapi.model

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T>(
        val items: List<T>
)

data class Question(
        @SerializedName("question_id")
        val questionId: Int?,

        val title: String?,
        val score: String?,

        @SerializedName("creation_date")
        val date: Long?
)