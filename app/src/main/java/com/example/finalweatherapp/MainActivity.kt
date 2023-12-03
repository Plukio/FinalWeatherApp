package com.example.finalweatherapp


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.finalweatherapp.model.data.LocationDetails
import com.example.finalweatherapp.model.data.User
import com.example.finalweatherapp.model.utils.EXTRA_Lat
import com.example.finalweatherapp.model.utils.EXTRA_Long
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false
    lateinit var amouht: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amouht = FirebaseAuth.getInstance()

        setContent {
            FinalWeatherAppTheme {
                // A surface container using the 'background' color from the theme

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White
                    ) {

                        var loading by remember { mutableStateOf(false) }
                        val context = LocalContext.current
                        var currentLocation by remember {
                            mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))
                        }

                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(p0: LocationResult) {
                                for (lo in p0.locations) {
                                    // Update UI with location data
                                    currentLocation = LocationDetails(lo.latitude, lo.longitude)
                                }
                            }
                        }

                        val launcherMultiplePermissions = rememberLauncherForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions()
                        ) { permissionsMap ->
                            val areGranted =
                                permissionsMap.values.reduce { acc, next -> acc && next }
                            if (areGranted) {
                                locationRequired = true
                                startLocationUpdates()
                                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        val name = remember{mutableStateOf("")}
                        val userid = amouht.currentUser?.uid?:""
                        val user = (application as AppApplication).repository.findUser(uid = userid).observeAsState()

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 250.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            val permissions = arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )


                            item {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(24.dp)
                                    , horizontalAlignment = Alignment.CenterHorizontally)
                                {
                                    Text(
                                        text = "A minimal weather app",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight(800),
                                        color = colorResource(id = R.color.black),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .align(Alignment.CenterHorizontally),
                                    )

                                    Box(modifier = Modifier.wrapContentHeight()) {
                                        Text(
                                            text = ("Welcome, " + if(!(user.value?.isEmpty()?:true)) {user.value?.first()?.name} else {
                                                "new user"
                                            }),
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight(800),
                                            color = colorResource(id = R.color.black),
                                            modifier = Modifier
                                                .wrapContentWidth()
                                        )
                                    }
                                }
                            }


                            if(!loading) {
                                item {
                                    if((user.value?.isEmpty()?:true)) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            OutlinedTextField(
                                                value = name.value,
                                                onValueChange = { name.value = it },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 48.dp, end = 48.dp),
                                                label = { Text(text = "What's your name?") },
                                                shape = RoundedCornerShape(50.dp)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(12.dp))

                                    if (!loading) {
                                        Button(
                                            onClick = {
                                                if (permissions.all {
                                                        ContextCompat.checkSelfPermission(
                                                            context,
                                                            it
                                                        ) == PackageManager.PERMISSION_GRANTED
                                                    }) {
                                                    loading = true
                                                    startLocationUpdates()

                                                    if((user.value?.isEmpty()?:true)) {
                                                        (application as AppApplication).repository.addUser(
                                                            User(
                                                                uid = userid,
                                                                name  = name.value ?: ""
                                                            )
                                                        )
                                                    }


                                                } else {
                                                    launcherMultiplePermissions.launch(
                                                        permissions
                                                    )
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(Color.Black)
                                        ) {
                                            Text(text = "Continue")
                                        }
                                    }
                                }
                            }

                            if (loading) {
                                item {
                                    val composition by rememberLottieComposition(
                                        spec = LottieCompositionSpec.RawRes(R.raw.loading),
                                    )

                                    val progress by animateLottieCompositionAsState(
                                        composition = composition,
                                        iterations = LottieConstants.IterateForever
                                    )

                                    if (currentLocation.latitude.toInt() == 0) {
                                        LottieAnimation(
                                            composition = composition,
                                            progress = {
                                                progress

                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )

            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                val locationDetails = LocationDetails(location.latitude, location.longitude)
                openDetails(locationDetails)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }

    private fun openDetails(Location: LocationDetails) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(EXTRA_Lat, Location.latitude)
        intent.putExtra(EXTRA_Long, Location.longitude)

        val handler = android.os.Handler()
        val runnable = Runnable {
            startActivity(intent)
        }
        handler.postDelayed(runnable, 1000)
    }
}









