package com.example.finalweatherapp.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.finalweatherapp.R
import com.example.finalweatherapp.model.utils.DateUtil
import com.example.finalweatherapp.model.utils.DateUtil.toFormattedDate
import com.example.finalweatherapp.model.utils.DateUtil.toFormattedDay
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme

@Composable
fun DailyLine(
    modifier: Modifier = Modifier,
    date: String,
    icon: String,
    maxTemp: String,
    minTemp: String,
    Desc: String,
) {
    ElevatedCard(
        modifier = modifier.padding(end = 12.dp, start = 6.dp),
        colors = CardDefaults.cardColors(containerColor  = colorResource(id = R.color.transX), contentColor = colorResource(id = R.color.black)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .width(240.dp).padding(16.dp)
                .wrapContentHeight()
                ,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,

            ) {

            Text(
                text = Desc, style = MaterialTheme.typography.bodyLarge, minLines = 2
            )

            LazyRow(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

            ) {


            item {
                Text(
                    modifier = Modifier.padding(start = 0.dp, end = 4.dp),
                    text = date.toFormattedDate() ?: "-",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

            }

            item {

                val url = "https://openweathermap.org/img/wn/$icon@2x.png"
                val painter = rememberAsyncImagePainter(url)

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                )
            }


            item {
                Column(
                    modifier = Modifier
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {

                    Text(
                        text = "Max " + ("%.2f".format(minTemp.toFloat() - 273.15)) + "°C",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )

                    Text(
                        text = "Min " + ("%.2f".format(maxTemp.toFloat() - 273.15)) + "°C",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                }


            }
        }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DailyLinePreview() {
    Surface {
        FinalWeatherAppTheme {
            DailyLine(
                date = "2023-12-02",
                icon = "10d",
                minTemp = "12",
                maxTemp=  "12",
                Desc = "Rainy"
            )
        }
    }
}