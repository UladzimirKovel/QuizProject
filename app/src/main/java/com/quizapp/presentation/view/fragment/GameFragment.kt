package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quizapp.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        loadButton()
    }

    private fun setupSpinners() {
        val categories = arrayOf("Linux", "DevOps", "Programming", "Cloud", "Docker", "Kubernetes")
        val limits = arrayOf("5", "10", "15", "20")
        val difficulties = arrayOf("Easy", "Medium", "Hard")

        // Настройка адаптеров для Spinner
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = categoryAdapter

        val limitAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, limits)
        limitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.limitSpinner.adapter = limitAdapter

        val difficultyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, difficulties)
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.difficultySpinner.adapter = difficultyAdapter
    }

    private fun loadButton() {
        binding.startButton.setOnClickListener {
            val category = binding.categorySpinner.selectedItem?.toString() ?: return@setOnClickListener
            val limits = binding.limitSpinner.selectedItem?.toString()?.toIntOrNull() ?: return@setOnClickListener
            val difficulty = binding.difficultySpinner.selectedItem?.toString() ?: return@setOnClickListener

            val action = GameFragmentDirections.actionGameFragmentToQuestionsFragment(
                category = category,
                limits = limits,
                difficulty = difficulty
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}