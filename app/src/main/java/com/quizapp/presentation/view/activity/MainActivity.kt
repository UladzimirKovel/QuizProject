package com.quizapp.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.quizapp.R
import com.quizapp.presentation.view.fragment.FavoriteFragment
import com.quizapp.presentation.view.fragment.GameFragment
import com.quizapp.presentation.view.fragment.ScoreFragment
import com.quizapp.presentation.view_model.LoginViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view).setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.score -> {
                    navController.navigate(R.id.scoreFragment)
                }

                R.id.favorite -> {
                    navController.navigate(R.id.favoriteFragment)
                }

                R.id.game -> {
                    navController.navigate(R.id.gameFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

        toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigation_view)

        navController = findNavController(R.id.fragmentContainerView)
        setOfActions()

        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteAcc -> {
                    viewModel.currentUser?.let { user ->
                        viewModel.deleteUser(user.user)
                        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.quit -> {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setOfActions() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.action_questionsFragment_to_favoriteFragment,
                R.id.action_questionsFragment_to_resultFragment,
                R.id.action_favoriteFragment_to_loginActivity,
                R.id.action_favoriteFragment_to_gameFragment,
                R.id.action_favoriteFragment_to_scoreFragment,
                R.id.action_gameFragment_to_loginActivity,
                R.id.action_gameFragment_to_scoreFragment,
                R.id.action_gameFragment_to_favoriteFragment,
                R.id.action_gameFragment_to_questionsFragment,
                R.id.action_resultFragment_to_loginActivity,
                R.id.action_resultFragment_to_gameFragment,
                R.id.action_resultFragment_to_scoreFragment,
                R.id.action_resultFragment_to_favoriteFragment,
                R.id.action_scoreFragment_to_gameFragment,
                R.id.action_scoreFragment_to_favoriteFragment,
                R.id.loginActivity,
                R.id.deleteAcc,
                R.id.scoreFragment
            )
        )
    }
}