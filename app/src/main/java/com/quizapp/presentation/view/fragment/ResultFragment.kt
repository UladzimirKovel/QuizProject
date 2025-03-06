package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quizapp.R
import com.quizapp.databinding.FragmentResultBinding
import com.quizapp.presentation.view_model.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val score = ResultFragmentArgs.fromBundle(requireArguments()).score
        val totalQuestions = ResultFragmentArgs.fromBundle(requireArguments()).totalQuestions

        binding.scoreTextView.text = "Ваш результат: $score из $totalQuestions"

        // Сохраняем результат
        viewModel.saveScore("user123", score, totalQuestions)

        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        binding.viewStatsButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_scoreFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}