package com.example.finalweatherapp

import android.content.Context
import android.util.Log
import com.example.finalweatherapp.model.data.LocationDetails
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.firebase.auth.FirebaseAuth
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


class MainActivityTest {

    @Test
    fun `AnoLogIn() should sign in anonymously and log success or failure`() {
        val mockFirebaseAuth = FirebaseAuth.getInstance()

        val mainActivity = MainActivity()
        mainActivity.amouht = mockFirebaseAuth

        mainActivity.AnoLogIn()

        verify(mockFirebaseAuth).signInAnonymously()
            .addOnSuccessListener { Log.d("TAG", "AnoLogin Suc $it") }
            .addOnFailureListener { Log.d("TAG", "AnoLogin Fal $it") }
    }

}