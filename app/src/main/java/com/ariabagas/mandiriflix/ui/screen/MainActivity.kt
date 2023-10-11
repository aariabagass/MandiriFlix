package com.ariabagas.mandiriflix.ui.screen

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.ActivityMainBinding
import com.ariabagas.mandiriflix.util.Constants
import com.ariabagas.mandiriflix.util.isDarkColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        updateThemeAndStatusBarOnDestinationChange(binding, navController)
    }

    private fun updateThemeAndStatusBarOnDestinationChange(
        binding: ActivityMainBinding, navController: NavController
    ) {
        with(WindowInsetsControllerCompat(window, window.decorView)) {
            navController.addOnDestinationChangedListener { _, destination, bundle ->
                window.statusBarColor = when (destination.id) {
                    R.id.searchFragment -> {
                        setTheme(R.style.Theme_MandiriFlix)
                        WindowCompat.setDecorFitsSystemWindows(window, true)
                        isAppearanceLightStatusBars = true

                        when (Build.VERSION.SDK_INT) {
                            21, 22 -> Color.TRANSPARENT
                            else -> ContextCompat.getColor(
                                this@MainActivity, R.color.day_night_inverse
                            )
                        }
                    }

                    else -> {
                        val backgroundColor = bundle?.getInt(Constants.BACKGROUND_COLOR) ?: 0
                        val isDarkBackground = backgroundColor.isDarkColor()
                        when (destination.id) {
                            else -> {
                                window.statusBarColor = backgroundColor
                                WindowCompat.setDecorFitsSystemWindows(window, false)
                                setTheme(if (isDarkBackground) R.style.DetailDarkTheme else R.style.DetailLightTheme)
                                isAppearanceLightStatusBars = !isDarkBackground
                                Color.TRANSPARENT
                            }
                        }
                    }
                }
                requestedOrientation = when (destination.id) {
                    R.id.youtubeFragment -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                    else -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }
            }
        }
    }


}