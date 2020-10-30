package com.example.retrofitstackoverflowapi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitstackoverflowapi.R
import com.example.retrofitstackoverflowapi.databinding.FragmentAnswersBinding
import com.example.retrofitstackoverflowapi.model.Question

class AnswersFragment : Fragment() {

    private var _binding: FragmentAnswersBinding? = null
    private val binding get() = _binding!!
    private var question: Question? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAnswersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            question = AnswersFragmentArgs.fromBundle(it).question
            binding.question = question
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}