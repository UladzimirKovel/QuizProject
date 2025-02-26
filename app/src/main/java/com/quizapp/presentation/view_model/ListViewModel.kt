package com.quizapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.model.QuizResponse
import com.quizapp.data.repository.RetrofitClient
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _questions = MutableLiveData<List<QuizResponse>>()
    val questions: LiveData<List<QuizResponse>> get() = _questions

    fun loadAllQuestions() {
        viewModelScope.launch {
            val apiKey = "HaMStVN8pjYI5NysWMiSpBDTzprHNeBOE3ORm6sn"
            val response = RetrofitClient.apiService.getAllQuestions(apiKey)
            _questions.value = response

            Log.e("Quiz", "$response")
        }
    }
}