package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.quizapp.R as non
import android.R
import com.quizapp.databinding.FragmentGameBinding
import com.quizapp.presentation.view_model.ListViewModel
import org.koin.android.ext.android.inject


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = arrayOf("Linux", "DevOps", "Programming", "Cloud", "Docker", "Kubernetes") // Пример категорий
        val limits = arrayOf("5", "10", "15", "20") // Пример лимитов
        val difficulties = arrayOf("Easy", "Medium", "Hard") // Пример сложностей

        // Настройка адаптеров для Spinner
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = categoryAdapter

        val limitAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, limits)
        limitAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.limitSpinner.adapter = limitAdapter

        val difficultyAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, difficulties)
        difficultyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.difficultySpinner.adapter = difficultyAdapter

        loadButton()

        viewModel.questions.observe(viewLifecycleOwner, Observer { questions ->
            if (questions.isNotEmpty()) {
                // Создаем Bundle для передачи данных
                val bundle = Bundle().apply {
                    putParcelableArrayList("questions", ArrayList(questions))
                }

                // Создаем QuestionsFragment и передаем Bundle
                val questionsFragment = QuestionsFragment().apply {
                    arguments = bundle
                }

                // Выполняем переход на QuestionsFragment
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView, questionsFragment)
//                    .addToBackStack(null)
//                    .commit()
//                findNavController().navigate(non.id.ac)
            }
        })
    }

    private fun loadButton() {
        binding.apply {
            startButton.setOnClickListener {
                val category = categorySpinner.selectedItem.toString()
                val limits = limitSpinner.selectedItem.toString().toInt()
                val difficulty = difficultySpinner.selectedItem.toString()

                viewModel.loadQuestions(category, limits, difficulty )
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}