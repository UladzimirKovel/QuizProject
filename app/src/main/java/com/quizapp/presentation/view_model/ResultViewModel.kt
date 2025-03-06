package com.quizapp.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.entities.ScoreEntity
import com.quizapp.domain.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(
    private val repository: ScoreRepository
) : ViewModel() {

    private val _result = MutableLiveData<List<ScoreEntity>>()
    val result: LiveData<List<ScoreEntity>> get() = _result

    fun loadScores() {
        viewModelScope.launch(Dispatchers.IO) {
            val scores = repository.getAllScore()
            _result.postValue(scores)
        }
    }

    fun saveScore(userId: String, score: Int, totalQuestions: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newScoreEntity = ScoreEntity(
                userId = userId,
                score = score,
                totalQuestions = totalQuestions,
                timestamp = System.currentTimeMillis()
            )
            repository.insertScore(newScoreEntity)
            loadScores()
        }
    }
}
