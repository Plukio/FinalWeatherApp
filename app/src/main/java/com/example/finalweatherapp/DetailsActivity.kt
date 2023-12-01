package com.example.finalweatherapp


import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalweatherapp.api.ACCESS_KEY
import com.example.finalweatherapp.api.EXCLUDE_FIELDS
import com.example.finalweatherapp.model.data.Daily
import com.example.finalweatherapp.model.data.Hourly
import com.example.finalweatherapp.model.data.ResopnseRaw
import com.example.finalweatherapp.model.utils.DateUtil.convertUnixTimestampToPST
import com.example.finalweatherapp.model.utils.DateUtil.toFormattedDay
import com.example.finalweatherapp.model.utils.EXTRA_Lat
import com.example.finalweatherapp.model.utils.EXTRA_Long
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme
import com.example.finalweatherapp.ui.theme.screen.DailyLine
import com.example.finalweatherapp.ui.theme.screen.ForecastComponent
import java.util.Locale


class DetailsActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val longi: Double = if (intent.hasExtra(EXTRA_Long)) {
            intent.extras?.get(EXTRA_Long).toString().toDouble()
        } else {
            0.0
        }

        val lati: Double = if (intent.hasExtra(EXTRA_Lat)) {
            intent.extras?.get(EXTRA_Lat).toString().toDouble()
        } else {
            0.0
        }

        val name = getCompleteAddressString(this, lati, longi)

        weatherViewModel.current(lati.toString(), longi.toString(), EXCLUDE_FIELDS, ACCESS_KEY)

        setContent {

            val theImage = weatherViewModel.currents.observeAsState()

            FinalWeatherAppTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {

                        val dateTime =
                            convertUnixTimestampToPST(theImage.value?.hourly?.get(0)?.dt ?: 0)
                        val date = dateTime.get(0)

                        item {
                            Box(modifier = Modifier.wrapContentHeight()) {
                                Text(
                                    text = "Today",
                                    fontSize = 110.sp,
                                    fontWeight = FontWeight(800),
                                    color = colorResource(id = R.color.black),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 24.dp)
                                )
                            }
                        }

                        val location = name?.split(",")

                        item {
                            Box(modifier = Modifier.wrapContentHeight()) {
                                Text(
                                    text = (location?.get(0) ?: "-") + "/" + (location?.get(1)
                                        ?: "-"),
                                    color = colorResource(id = R.color.gray),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(800),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 32.dp)
                                )
                            }
                        }

                        item { Spacer(Modifier.heightIn(0.dp)) }

                        item {
                            Box(modifier = Modifier.wrapContentHeight()) {
                                Text(
                                    text = theImage.value?.daily?.get(0)?.summary ?: "-",
                                    fontSize = 24.sp,
                                    color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight(800),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 32.dp)
                                )
                            }
                        }



                        item { Spacer(Modifier.heightIn(4.dp)) }

                        item {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                            ) { listForecast(theImage) }
                        }

                        item { Spacer(Modifier.heightIn(8.dp)) }

                        item {
                            Divider(
                                modifier = Modifier.padding(start = 24.dp, end = 16.dp),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                        }

                        item() { Spacer(Modifier.height(12.dp)) }

                        item {
                            Box(modifier = Modifier.wrapContentHeight()) {
                                Text(
                                    text = "Other Days",
                                    color = colorResource(id = R.color.black),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight(800),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 32.dp)
                                )
                            }
                        }

                        item {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                            ) { listDaily(theImage) }
                        }

                        item { Spacer(Modifier.heightIn(8.dp)) }

                        item {
                            Divider(
                                modifier = Modifier.padding(start = 24.dp, end = 16.dp),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                        }

                        item { Spacer(Modifier.heightIn(16.dp)) }

                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(start=32.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        val intent: Intent = Intent(
                                            this@DetailsActivity,
                                            Start::class.java
                                        )
                                        startActivity(intent)
                                    }, colors = ButtonDefaults.buttonColors(colorResource(id = R.color.trans))
                                ) {
                                    Text(text = "< Back", color = Color.Black)
                                }
                            }
                        }

                    }
                }

            }

        }
    }
}

private fun getCompleteAddressString(context: Context, lati: Double, longi: Double): String? {
    var strAdd = ""
    val geocoder = Geocoder(context, Locale.getDefault())

    try {
        val addresses: List<Address>? = geocoder.getFromLocation(lati, longi, 1)
        if (addresses != null) {
            val returnedAddress: Address = addresses[0]
            val countryName = returnedAddress.countryName
            val locality = returnedAddress.adminArea

            strAdd = "$countryName,$locality"
            Log.w("My Current loction address", strAdd)
        } else {
            Log.w("My Current loction address", "No Address returned!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.w("My Current loction address", "Canont get Address!")
    }

    return strAdd
}



@Composable
fun listForecast(forecast: State<ResopnseRaw?>) {
    LazyRow(
        modifier = Modifier.padding(18.dp)
    ){
        forecast.value?.let  {
            items(it.hourly.slice(0..it.hourly.size.minus(1))){ item: Hourly ->
                ForecastComponent(
                    Modifier, convertUnixTimestampToPST(item?.dt ?: 0).get(1),
                    item.weather.first().icon,
                    item.temp.toString(),
                    item.weather.first().description,
                )
            }
        }
    }
}

@Composable
fun listDaily(forecast: State<ResopnseRaw?>) {
    LazyRow(
        modifier = Modifier.padding(18.dp)
    ){
        forecast.value?.let  {
            items(it.daily.slice(1..it.daily.size.minus(1))){ item: Daily ->
                DailyLine(
                    Modifier, date = convertUnixTimestampToPST(item?.dt?: 0).get(0),
                    icon = item?.weather?.first()?.icon ?: "-",
                    minTemp = item?.temp?.min?: "24",
                    maxTemp = item?.temp?.max?: "24",
                    Desc = item?.summary ?: "-"
                )
            }
        }
    }
}



