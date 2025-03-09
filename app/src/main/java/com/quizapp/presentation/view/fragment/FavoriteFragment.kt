package com.quizapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.quizapp.databinding.FragmentFavoriteBinding
import com.quizapp.presentation.adapter.FavoriteAdapter
import com.quizapp.presentation.view_model.FavoriteViewModel
import com.quizapp.presentation.view_model.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        loginViewModel.currentUser?.let { user ->
            viewModel.loadFavoriteQuizzes(user.user)
        }

        viewModel.favoriteQuizzes.observe(viewLifecycleOwner) { quizzes ->
            favoriteAdapter.submitList(quizzes)
        }
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter { score ->
            viewModel.deleteFavoriteQuiz(score.id, score.userId)
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        binding.acbDeleteAllQuizzes.setOnClickListener {
            loginViewModel.currentUser?.let { user ->
                viewModel.deleteAllFavoriteQuizzes(user.user)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}