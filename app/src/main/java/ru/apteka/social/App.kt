package ru.apteka.social

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Представляет приложение.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}