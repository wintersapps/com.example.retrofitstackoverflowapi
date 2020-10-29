package com.example.retrofitstackoverflowapi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitstackoverflowapi.databinding.FragmentQuestionsBinding
import com.example.retrofitstackoverflowapi.view.adapters.QuestionsAdapter
import com.example.retrofitstackoverflowapi.viewmodel.QuestionsViewModel

class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var viewModel: QuestionsViewModel
    private val questionsAdapter = QuestionsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)

        binding.questionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = questionsAdapter
        }

        observeViewModel()

        viewModel.getQuestions()
    }
    private fun observeViewModel(){
        viewModel.questionsResponse.observe(viewLifecycleOwner, { items ->
            items?.let {
                binding.questionsRecyclerView.visibility = View.VISIBLE
                questionsAdapter.addQuestions(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { errorMsg ->
            binding.loadingErrorTextView.visibility = if (errorMsg == null) View.GONE else View.VISIBLE
            val errorMessage = "Error\n$errorMsg"
            binding.loadingErrorTextView.text = errorMessage
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.loadingQuestionsProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.loadingErrorTextView.visibility = View.GONE
                    binding.questionsRecyclerView.visibility = View.GONE
                }
            }
        })
    }

}