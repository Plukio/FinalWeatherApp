package com.example.finalweatherapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme
import com.google.firebase.auth.FirebaseAuth

class Start: ComponentActivity() {
    lateinit var amouht: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        amouht = FirebaseAuth.getInstance()
        AnoLogIn(amouht)

        setContent {
            FinalWeatherAppTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(R.raw.loading),
                    )

                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress })

                }
            }
        }
    }

    fun AnoLogIn(amouht: FirebaseAuth) {
        amouht.signInAnonymously().addOnSuccessListener {
            val intent: Intent = Intent(
                this@Start,
                MainActivity::class.java
            )
            startActivity(intent)
        }
            .addOnFailureListener {
                { Log.d("TAG", "AnoLogin Fal $it") }
            }
    }
}