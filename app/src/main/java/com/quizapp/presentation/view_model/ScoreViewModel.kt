package com.quizapp.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.entities.ScoreEntity
import com.quizapp.domain.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoreViewModel(
    private val repository: ScoreRepository
): ViewModel() {

    private val _score = MutableLiveData<List<ScoreEntity>>()
    val score: LiveData<List<ScoreEntity>> get() = _score

    fun loadScores() {
        viewModelScope.launch(Dispatchers.IO) {
            val scores = repository.getAllScore()
            _score.postValue(scores)
        }
    }

    fun deleteScore() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllScore()
            _score.postValue(emptyList())
        }
    }

    fun deleteScoreById(scoreId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuiz(scoreId)
            loadScores() // Перезагружаем список после удаления
        }
    }
}