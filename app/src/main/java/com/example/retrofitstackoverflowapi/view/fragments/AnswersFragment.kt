package com.example.retrofitstackoverflowapi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstackoverflowapi.databinding.FragmentAnswersBinding
import com.example.retrofitstackoverflowapi.model.Question
import com.example.retrofitstackoverflowapi.view.adapters.AnswersAdapter
import com.example.retrofitstackoverflowapi.viewmodel.AnswersViewModel

class AnswersFragment : Fragment() {

    private var _binding: FragmentAnswersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnswersViewModel
    private var question: Question? = null
    private val answersAdapter = AnswersAdapter(arrayListOf())
    private lateinit var lm: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAnswersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnswersViewModel::class.java)

        lm = LinearLayoutManager(binding.root.context)
        arguments?.let {
            question = AnswersFragmentArgs.fromBundle(it).question
            binding.question = question

            binding.answersRecyclerView.apply {
                layoutManager = lm
                adapter = answersAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if(dy > 0){
                            val childCount = answersAdapter.itemCount
                            val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                            if(childCount - 1 == lastPosition
                                && binding.loadingAnswersProgressBar.visibility == View.GONE){
                                binding.loadingAnswersProgressBar.visibility = View.VISIBLE
                                viewModel.getNextPage(question!!.questionId)
                            }
                        }
                    }
                })
            }

            viewModel.getNextPage(question!!.questionId)
        }

        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel(){
        viewModel.answersResponse.observe(viewLifecycleOwner, { items ->
            items?.let {
                binding.answersRecyclerView.visibility = View.VISIBLE
                answersAdapter.addAnswers(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            binding.loadingErrorTextView.visibility = if (errorMessage == null) View.GONE else View.VISIBLE
            binding.loadingErrorTextView.text = errorMessage
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.loadingAnswersProgressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    binding.loadingErrorTextView.visibility = View.GONE
                    binding.answersRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}