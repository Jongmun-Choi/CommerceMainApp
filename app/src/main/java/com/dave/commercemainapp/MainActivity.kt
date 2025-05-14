package com.dave.commercemainapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dave.commercemainapp.view.MainScreen
import com.dave.commercemainapp.view.theme.CommerceMainAppTheme
import com.dave.commercemainapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        viewModel.getFavorite()
        setContent {
            CommerceMainAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}