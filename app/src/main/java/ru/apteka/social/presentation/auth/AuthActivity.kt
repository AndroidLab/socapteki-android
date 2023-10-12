package ru.apteka.social.presentation.auth

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.data.utils.transparentStatusBar
import ru.apteka.social.R


/**
 * Представляет активити авторизации.
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //transparentStatusBar()
        //window.statusBarColor = Color.TRANSPARENT
        //WindowCompat.getInsetsController(window, View(this)).isAppearanceLightStatusBars = true
    }
}