package com.dave.commercemainapp.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.dave.commercemainapp.R
import com.dave.commercemainapp.view.theme.KurlyPuple


@Composable
fun SplashScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = KurlyPuple),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.kurly_logo), contentDescription = "kurly logo")
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()

}