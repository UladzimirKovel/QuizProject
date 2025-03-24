package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.quizapp.R
import com.quizapp.databinding.FragmentResultBinding
import com.quizapp.presentation.view_model.FavoriteViewModel
import com.quizapp.presentation.view_model.LoginViewModel
import com.quizapp.presentation.view_model.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val args: ResultFragmentArgs by navArgs()

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

        clickInit()
    }

    private fun clickInit() {
        val score = args.score
        val totalQuestions = args.totalQuestions

        // Отображаем результат
        binding.actvScore.text = "Your score: $score/$totalQuestions"

        binding.acbPlayAgain.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        binding.acbFavoriteSave.setOnClickListener {
            loginViewModel.currentUser?.let { user ->
                loginViewModel.registrationUser // Проверить 
                favoriteViewModel.addFavoriteQuiz(
                    userId = user.user,
                    question = args.question
                )
                // Сделать так,чтобы сохранялся квиз только этого пользователя
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }

        binding.acbSave.setOnClickListener {
            // Сохраняем результат
            loginViewModel.currentUser?.let { user ->
                viewModel.saveScore(user.user, score, totalQuestions)
                Toast.makeText(requireContext(), "The result was saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}