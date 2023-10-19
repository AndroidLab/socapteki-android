package ru.apteka.social

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import ru.tinkoff.decoro.BuildConfig

/**
 * Представляет приложение.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("4e8c6ec3-ab89-4bca-b59d-fb84b8c621e3")
    }

}