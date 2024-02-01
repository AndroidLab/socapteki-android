package ru.apteka.social

import android.app.Application
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

/**
 * Представляет приложение.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MapKitFactory.setApiKey("4e8c6ec3-ab89-4bca-b59d-fb84b8c621e3")
        // SberIDInitializer.initialize(this, "663f4083-5f20-462b-ac81-ef09bd5609d2")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // Log.d("myL", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Log.d("myL", "msg_token_fmt " + task.result)
            }
        )
    }
}
