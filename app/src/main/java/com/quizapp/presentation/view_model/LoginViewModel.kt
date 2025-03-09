package com.quizapp.presentation.view_model

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.entities.UserEntity
import com.quizapp.domain.repository.UserRepository
import com.quizapp.domain.use_cases.isEmailValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _registrationUser = MutableLiveData<Boolean?>()
    val registrationUser: LiveData<Boolean?> get() = _registrationUser

    private val _deleteUserStatus = MutableLiveData<Boolean?>()
    val deleteUserStatus: LiveData<Boolean?> get() = _deleteUserStatus

    var currentUser: UserEntity? = null

    fun registrationUser(user: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.registrationUser(user, email)
            _registrationUser.postValue(success)
        }
    }

    fun loginUser(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.loginUser(user)
            _registrationUser.postValue(success != null)
            currentUser = success
        }
    }

    fun deleteUser(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
            _deleteUserStatus.postValue(true)
            currentUser = null
        }
    }

    fun resetState() = _registrationUser.postValue(null)

    fun validateInput(
        name: EditText,
        email: EditText,
        context: Context
    ): Boolean {
        val name = name.text.toString().trim()
        val email = email.text.toString().trim()

        return when {
            email.isEmpty() -> {
                Toast.makeText(context, "Email string is empty", Toast.LENGTH_LONG).show()
                false
            }

            !isEmailValid(email) -> {
                Toast.makeText(context, "Incorrect Email validation", Toast.LENGTH_LONG).show()
                false
            }

            name.isEmpty() -> {
                Toast.makeText(context, "The name field cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }

            name.length !in 3..10 -> {
                Toast.makeText(
                    context,
                    "The name field should be from 3 to 25 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            else -> true
        }
    }

    fun validateUser(
        name: EditText, context: Context
    ): Boolean {
        val name = name.text.toString().trim()

        return when {
            name.isEmpty() -> {
                Toast.makeText(context, "The name field cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }

            name.length !in 3..10 -> {
                Toast.makeText(
                    context,
                    "The name field should be from 3 to 25 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            else -> true
        }
    }
}