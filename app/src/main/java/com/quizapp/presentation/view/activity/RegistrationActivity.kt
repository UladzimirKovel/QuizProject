package com.quizapp.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.quizapp.databinding.ActivityRegistrationBinding
import com.quizapp.presentation.view_model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationActivity: AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: ActivityRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)

        /** Подписываемся на обновление и проверяем,сущ ли пользователь с таким email */
        viewModel.registrationUser.observe(this, Observer { success ->
            success?.let {
                if(it) {
                    startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                    viewModel.resetState()
                } else Toast.makeText(
                    this,
                    "Incorrect user name or email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        setupListener()
        setContentView(binding.root)
    }

    private fun setupListener() {
        binding.apply {
            acbEnter.setOnClickListener {
                if (viewModel.validateInput(
                        etRegistration, etEmail, this@RegistrationActivity
                    )
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.registrationUser(etRegistration.text.toString(), etEmail.text.toString())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        viewModel.registrationUser.removeObservers(this)
    }
}