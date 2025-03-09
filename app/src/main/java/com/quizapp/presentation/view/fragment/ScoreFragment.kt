package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.quizapp.databinding.FragmentScoreBinding
import com.quizapp.presentation.adapter.ScoreAdapter
import com.quizapp.presentation.view_model.ScoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreViewModel by viewModel()
    private lateinit var scoreAdapter: ScoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Загружаем результаты
        viewModel.loadScores()

        // Наблюдаем за изменениями в списке результатов
        viewModel.score.observe(viewLifecycleOwner) { scores ->
            scoreAdapter.submitList(scores)
        }
    }

    private fun setupRecyclerView() {
        scoreAdapter = ScoreAdapter { score ->
            viewModel.deleteScoreById(score.id)
        }

        binding.rvScores.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scoreAdapter
        }

        // Настраиваем кнопку очистки результатов
        binding.acbDeleteAllQuizzes.setOnClickListener {
            viewModel.deleteScore()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}