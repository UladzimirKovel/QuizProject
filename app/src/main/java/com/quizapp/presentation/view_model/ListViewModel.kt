package com.quizapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.model.QuizResponse
import com.quizapp.data.repository.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _questions = MutableLiveData<List<QuizResponse>>()
    val questions: LiveData<List<QuizResponse>> get() = _questions

    private val apiKey = "HaMStVN8pjYI5NysWMiSpBDTzprHNeBOE3ORm6sn"

    fun loadAllQuestions() {
        viewModelScope.launch {

            val response = RetrofitClient.apiService.getAllQuestions(apiKey)
            _questions.value = response

            Log.e("Quiz", "$response")
        }
    }

    fun loadQuestions(category: String, limit: Int, difficulty: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getQuestions(category, limit, difficulty, apiKey)
                _questions.value = response

                Log.e("Questions", "$response")
            } catch (e: Exception) {

            }
        }
    }
}