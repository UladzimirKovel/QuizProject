package com.quizapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.entities.FavoriteEntity
import com.quizapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favoriteQuizzes = MutableLiveData<List<FavoriteEntity>>()
    val favoriteQuizzes: LiveData<List<FavoriteEntity>> get() = _favoriteQuizzes

    fun loadFavoriteQuizzes(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val quizzes = repository.getFavoriteQuizzes(userId)
            _favoriteQuizzes.postValue(quizzes)
        }
    }

    fun addFavoriteQuiz(userId: String, question: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteQuiz = FavoriteEntity(
                userId = userId,
                question = question,
                timestamp = System.currentTimeMillis()
            )
            repository.addFavoriteQuiz(favoriteQuiz)
        }
    }

    fun deleteFavoriteQuiz(quizId: Long, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteQuiz(quizId)
            loadFavoriteQuizzes(userId)
        }
    }

    fun deleteAllFavoriteQuizzes(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavoriteQuizzes(userId)
            _favoriteQuizzes.postValue(emptyList())
        }
    }
}