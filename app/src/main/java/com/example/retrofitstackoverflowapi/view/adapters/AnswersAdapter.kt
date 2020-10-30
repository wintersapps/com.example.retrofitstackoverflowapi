package com.example.retrofitstackoverflowapi.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstackoverflowapi.databinding.AnswerLayoutBinding
import com.example.retrofitstackoverflowapi.model.Answer

class AnswersAdapter(private val answers: ArrayList<Answer>): RecyclerView.Adapter<AnswersAdapter.AdapterViewHolder>() {

    fun addAnswers(newAnswers: List<Answer>){
        val currentLength = answers.size
        answers.addAll(newAnswers)
        notifyItemInserted(currentLength)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = AnswerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.view.answer = answers[position]
    }

    override fun getItemCount() = answers.size

    class AdapterViewHolder(var view: AnswerLayoutBinding): RecyclerView.ViewHolder(view.root)
}