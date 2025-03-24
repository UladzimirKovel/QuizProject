package com.quizapp.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.quizapp.databinding.ActivityLoginBinding
import com.quizapp.presentation.view_model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        viewModel.currentUser?.let {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        viewModel.registrationUser.observe(this, Observer { success ->
            success?.let {
                if(it) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    viewModel.resetState()
                } else Toast.makeText(
                    this,
                    "User with this name don't registration",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        setupListener()

        setContentView(binding.root)
    }

    private fun setupListener() {
        binding.apply {
            acbEnter.setOnClickListener {
                if (viewModel.validateUser(
                        etLogin, this@LoginActivity
                    )
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.loginUser(etLogin.text.toString())
                    }
                }
            }
            acbRegistration.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        viewModel.registrationUser.removeObservers(this)
    }
}