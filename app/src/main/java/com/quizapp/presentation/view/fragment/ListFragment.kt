package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.quizapp.databinding.FragmentListBinding
import com.quizapp.presentation.adapter.ListAdapter
import com.quizapp.presentation.view_model.ListViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by inject()
    private val adapter: ListAdapter by inject { parametersOf(findNavController()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initRecycler()

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            adapter.upQuestions(questions)
        }

        viewModel.loadAllQuestions()
    }

    private fun initRecycler() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}