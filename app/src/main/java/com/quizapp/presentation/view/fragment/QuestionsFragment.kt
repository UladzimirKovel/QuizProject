package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quizapp.R
import com.quizapp.databinding.FragmentQuestionsBinding
import com.quizapp.domain.model.GameState
import com.quizapp.presentation.view_model.QuestionsViewModel
import org.koin.android.ext.android.inject

class QuestionsFragment : Fragment() {

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

        viewModel.loadQuestions(category, difficulty, questionCount)
        setupObservers()

        clickInit()
    }

    private fun clickInit() {
        binding.acbBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.gameState.observe(viewLifecycleOwner) { state ->
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
                    val currentQuestion = state.currentQuestion.question

                    val action =
                        QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(
                            score = finalScore,
                            totalQuestions = total,
                            question = currentQuestion
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
            acbAnswer5.isVisible = !show
            acbAnswer6.isVisible = !show
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ошибка загрузки вопросов", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun updateUI(state: GameState.Playing) {
        binding.apply {
            tvQuestion.text = state.currentQuestion.question
            tvNumberQuestion.text =
                "Question ${state.currentIndex + 1} of ${state.totalQuestions} (Score: ${state.score})"

            val answerButtons =
                listOf(acbAnswer1, acbAnswer2, acbAnswer3, acbAnswer4, acbAnswer5, acbAnswer6)

            // Очищаем все кнопки и включаем их
            answerButtons.forEach { button ->
                button.apply {
                    text = ""
                    isVisible = false
                    setOnClickListener(null)
                    setBackgroundResource(R.drawable.acb_bg) // Сбрасываем цвет фона
                    isEnabled = true // Включаем кнопку
                }
            }

            // Получаем ответы из Map
            val currentAnswers = state.currentQuestion.answers
            currentAnswers?.let { answers ->
                // Устанавливаем ответы на кнопки
                val answersList = listOf(
                    "answer_a_correct" to (answers["answer_a"] ?: ""),
                    "answer_b_correct" to (answers["answer_b"] ?: ""),
                    "answer_c_correct" to (answers["answer_c"] ?: ""),
                    "answer_d_correct" to (answers["answer_d"] ?: ""),
                    "answer_e_correct" to (answers["answer_e"] ?: ""),
                    "answer_f_correct" to (answers["answer_f"] ?: "")
                )

                answersList.forEachIndexed { index, (key, value) ->
                    if (index < answerButtons.size && value.isNotEmpty()) {
                        answerButtons[index].apply {
                            text = value
                            isVisible = true
                            setOnClickListener {

                                // Проверяем правильность ответа
                                val isCorrect = state.currentQuestion.correctAnswers?.get(key)
                                    ?.lowercase() in listOf("true", "1", "yes")

                                // Меняем цвет кнопки в зависимости от правильности ответа
                                if (isCorrect) {
                                    setBackgroundColor(
                                        resources.getColor(
                                            R.color.correct_answer,
                                            null
                                        )
                                    )
                                } else {
                                    setBackgroundColor(
                                        getResources().getColor(
                                            R.color.wrong_answer,
                                            null
                                        )
                                    )
                                }

                                // Отключаем все кнопки после ответа
                                answerButtons.forEach { it.isEnabled = false }

                                // Задержка перед переходом к следующему вопросу
                                postDelayed({
                                    viewModel.checkAnswer(key)
                                }, 1500)
                            }
                        }
                    }
                }
            } ?: run {
                showError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}