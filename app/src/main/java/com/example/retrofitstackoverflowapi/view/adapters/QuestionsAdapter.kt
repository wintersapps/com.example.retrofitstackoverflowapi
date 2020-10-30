package com.example.retrofitstackoverflowapi.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstackoverflowapi.databinding.QuestionLayoutBinding
import com.example.retrofitstackoverflowapi.model.Question
import com.example.retrofitstackoverflowapi.view.fragments.QuestionsFragmentDirections

class QuestionsAdapter(private val questions: ArrayList<Question>):
    RecyclerView.Adapter<QuestionsAdapter.AdapterViewHolder>() {

    fun addQuestions(newQuestions: List<Question>) {
        val currentLength = questions.size
        questions.addAll(newQuestions)
        notifyItemInserted(currentLength)
    }

    fun clearQuestions() {
        questions.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = QuestionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.view.question = questions[position]

        holder.view.questionLayout.setOnClickListener {
            val action = QuestionsFragmentDirections.actionGoToAnswers(questions[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = questions.size

    class AdapterViewHolder(var view: QuestionLayoutBinding): RecyclerView.ViewHolder(view.root)
}