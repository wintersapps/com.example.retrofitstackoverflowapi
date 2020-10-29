package com.example.retrofitstackoverflowapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitstackoverflowapi.model.Question

class QuestionsViewModel: ViewModel() {

    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getQuestions() {
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}