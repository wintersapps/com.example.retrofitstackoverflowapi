package com.example.retrofitstackoverflowapi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstackoverflowapi.databinding.FragmentQuestionsBinding
import com.example.retrofitstackoverflowapi.view.adapters.QuestionsAdapter
import com.example.retrofitstackoverflowapi.viewmodel.QuestionsViewModel

class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var viewModel: QuestionsViewModel
    private val questionsAdapter = QuestionsAdapter(arrayListOf())
    private lateinit var lm: LinearLayoutManager

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

        lm = LinearLayoutManager(binding.root.context)
        binding.questionsRecyclerView.apply {
            layoutManager = lm
            adapter = questionsAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dy > 0){
                        val childCount = questionsAdapter.itemCount
                        val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                        if(childCount - 1 == lastPosition
                                && binding.loadingQuestionsProgressBar.visibility == View.GONE){
                            viewModel.getNextPage()
                            binding.loadingQuestionsProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        observeViewModel()

        viewModel.getNextPage()

        binding.questionsSwipeRefreshLayout.setOnRefreshListener {
            questionsAdapter.clearQuestions()
            viewModel.getFirstPage()
            binding.questionsRecyclerView.visibility = View.GONE
            binding.loadingQuestionsProgressBar.visibility = View.VISIBLE
        }
    }
    private fun observeViewModel(){
        viewModel.questionsResponse.observe(viewLifecycleOwner, { items ->
            items?.let {
                binding.questionsRecyclerView.visibility = View.VISIBLE
                binding.questionsSwipeRefreshLayout.isRefreshing = false
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