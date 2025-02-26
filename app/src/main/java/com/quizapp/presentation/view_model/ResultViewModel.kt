package com.quizapp.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quizapp.data.model.QuizResponse

class ResultViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<QuizResponse>>()
    val categories: LiveData<List<QuizResponse>> get() = _categories


}
