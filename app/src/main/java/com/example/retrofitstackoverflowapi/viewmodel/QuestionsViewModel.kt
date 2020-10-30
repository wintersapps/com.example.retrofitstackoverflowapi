package com.example.retrofitstackoverflowapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitstackoverflowapi.model.Question
import com.example.retrofitstackoverflowapi.model.ResponseWrapper
import com.example.retrofitstackoverflowapi.model.StackOverflowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel : ViewModel() {

    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getQuestions() {
        StackOverflowService.api.getQuestions(1)
                .enqueue(object : Callback<ResponseWrapper<Question>> {
                    override fun onResponse(call: Call<ResponseWrapper<Question>>,
                                            response: Response<ResponseWrapper<Question>>) {
                        if(response.isSuccessful){
                            val questions = response.body()
                            questions?.let {
                                questionsResponse.value = it.items
                                loading.value = false
                                error.value = null
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseWrapper<Question>>, t: Throwable) {
                        onError(t.localizedMessage)
                    }

                })
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}