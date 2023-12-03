package com.example.finalweatherapp.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.example.finalweatherapp.model.utils.DateUtil.convertToAmPm
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme

@Composable
fun ForecastComponent(
    modifier: Modifier = Modifier,
    date: String,
    icon: String,
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
                .defaultMinSize(minWidth = 130.dp)
                .wrapContentWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = convertToAmPm(date),
                style = MaterialTheme.typography.titleLarge
            )

            val url = "https://openweathermap.org/img/wn/$icon@2x.png"
            val painter = rememberAsyncImagePainter(url)

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
            )

            Text(
                text = ("%.2f".format(minTemp.toFloat() - 273.15)) + "°C",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.width(4.dp))

            Text(
                text = Desc, style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForecastComponentPreview() {
    Surface {
        FinalWeatherAppTheme {
            ForecastComponent(
                date = "17:00:00",
                icon = "10d",
                minTemp = "12",
                Desc = "Rainy",
            )
        }
    }
}