package com.quizapp

import android.app.Application
import com.quizapp.di.favoriteModule
import com.quizapp.di.loginViewModelModule
import com.quizapp.di.resultViewModelModule
import com.quizapp.di.questionViewModelModule
import com.quizapp.di.scoreModule
import com.quizapp.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        koinInit()
    }

    private fun koinInit() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                loginViewModelModule,
                resultViewModelModule,
                userModule,
                questionViewModelModule,
                scoreModule,
                favoriteModule
            )
        }
    }
}