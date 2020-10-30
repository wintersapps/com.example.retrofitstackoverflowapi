package com.example.retrofitstackoverflowapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitstackoverflowapi.model.Answer
import com.example.retrofitstackoverflowapi.model.ResponseWrapper
import com.example.retrofitstackoverflowapi.model.StackOverflowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswersViewModel: ViewModel() {

    val answersResponse = MutableLiveData<List<Answer>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    var questionId = 0
    var page = 0

    fun getNextPage(questionId: Int?){
        questionId?.let {
            this.questionId = it
            this.page++
            getAnswers()
        }
    }

    private fun getAnswers(){
        StackOverflowService.getAnswers(questionId, page)
            .enqueue(object: Callback<ResponseWrapper<Answer>>{
                override fun onResponse(
                    call: Call<ResponseWrapper<Answer>>,
                    response: Response<ResponseWrapper<Answer>>
                ) {
                    val answers = response.body()
                    answers?.let {
                        answersResponse.value = it.items
                        loading.value = false
                        error.value = null
                    }
                }

                override fun onFailure(call: Call<ResponseWrapper<Answer>>, t: Throwable) {
                    onError(t.localizedMessage)
                }
            })
    }

    private fun onError(message: String){
        error.value = message
        loading.value = false
    }
}