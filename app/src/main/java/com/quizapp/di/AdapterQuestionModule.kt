package com.quizapp.di

import androidx.navigation.NavController
import com.quizapp.presentation.adapter.ListAdapter
import org.koin.dsl.module

val adapterQuestionModule = module {
    factory {(navController: NavController) ->
        ListAdapter(mutableListOf(), navController) }
}