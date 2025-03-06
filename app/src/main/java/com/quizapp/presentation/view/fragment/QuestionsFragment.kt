package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quizapp.databinding.FragmentQuestionsBinding
import com.quizapp.domain.model.GameState
import com.quizapp.presentation.view_model.QuestionsViewModel
import org.koin.android.ext.android.inject

class QuestionsFragment : Fragment() {

    private val TAG = "QuestionsFragment"
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = QuestionsFragmentArgs.fromBundle(requireArguments()).category
        val difficulty = QuestionsFragmentArgs.fromBundle(requireArguments()).difficulty
        val questionCount = QuestionsFragmentArgs.fromBundle(requireArguments()).limits

        Log.d(TAG, "Starting new game: category=$category, difficulty=$difficulty, count=$questionCount")
        viewModel.loadQuestions(category, difficulty, questionCount)
        setupObservers()
        
        binding.acbBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.gameState.observe(viewLifecycleOwner) { state ->
            Log.d(TAG, "Game state changed: $state")
            when (state) {
                is GameState.Loading -> {
                    showLoading(true)
                }
                is GameState.Playing -> {
                    showLoading(false)
                    updateUI(state)
                }
                is GameState.Finished -> {
                    val finalScore = state.finalScore
                    val total = state.totalQuestions
                    Log.d(TAG, "Game finished - Score: $finalScore/$total")
                    
                    val action = QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(
                        score = finalScore,
                        totalQuestions = total
                    )
                    findNavController().navigate(action)
                }
                is GameState.Error -> {
                    showLoading(false)
                    showError()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.apply {
            tvQuestion.isVisible = !show
            tvNumberQuestion.isVisible = !show
            acbAnswer1.isVisible = !show
            acbAnswer2.isVisible = !show
            acbAnswer3.isVisible = !show
            acbAnswer4.isVisible = !show
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ошибка загрузки вопросов", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun updateUI(state: GameState.Playing) {
        binding.apply {
            tvQuestion.text = state.currentQuestion.question
            tvNumberQuestion.text = "Вопрос ${state.currentIndex + 1} из ${state.totalQuestions} (Счет: ${state.score})"
            
            val answerButtons = listOf(acbAnswer1, acbAnswer2, acbAnswer3, acbAnswer4)
            
            // Очищаем все кнопки
            answerButtons.forEach { button ->
                button.apply {
                    text = ""
                    isVisible = false
                    setOnClickListener(null)
                }
            }

            // Логируем текущий вопрос
            Log.d(TAG, "Showing question ${state.currentIndex + 1}/${state.totalQuestions}")
            Log.d(TAG, "Question: ${state.currentQuestion.question}")
            Log.d(TAG, "Current score: ${state.score}")
            
            // Получаем ответы из Map
            val currentAnswers = state.currentQuestion.answers
            Log.d(TAG, "Answers:")
            currentAnswers?.let { answers ->
                Log.d(TAG, "  A: '${answers["answer_a"]}'")
                Log.d(TAG, "  B: '${answers["answer_b"]}'")
                Log.d(TAG, "  C: '${answers["answer_c"]}'")
                Log.d(TAG, "  D: '${answers["answer_d"]}'")
                
                // Устанавливаем ответы на кнопки
                val answersList = listOf(
                    "answer_a" to (answers["answer_a"] ?: ""),
                    "answer_b" to (answers["answer_b"] ?: ""),
                    "answer_c" to (answers["answer_c"] ?: ""),
                    "answer_d" to (answers["answer_d"] ?: "")
                )
                
                answersList.forEachIndexed { index, (key, value) ->
                    if (index < answerButtons.size && value.isNotEmpty()) {
                        answerButtons[index].apply {
                            text = value
                            isVisible = true
                            setOnClickListener {
                                Log.d(TAG, "Selected answer - Key: '$key', Value: '$value'")
                                viewModel.checkAnswer(key)
                            }
                        }
                    }
                }
            } ?: run {
                Log.e(TAG, "Answers map is null")
                showError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}