package com.dave.commercemainapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dave.commercemainapp.ui.MainScreen
import com.dave.commercemainapp.ui.theme.CommerceMainAppTheme
import com.dave.commercemainapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        viewModel.getFavorite()
        setContent {
            CommerceMainAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}