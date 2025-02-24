package com.example.weatherapp.uiux

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.Hour
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.WeatherViewModel

@Composable
fun ForecastScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val forecast by viewModel.forecastData.collectAsState()
    println("Forecast Data in UI: $forecast") // Logging
    val key = "3fb96a0af1ba4680bb683923251502"
    var query by remember {
        mutableStateOf("")
    }
    var days by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.padding(8.dp)
        .paint(painterResource(R.drawable.img), contentScale = ContentScale.FillBounds)
    ){
        Column (modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query=it
                },
                placeholder = { Text("City") }
            )
            OutlinedTextField(
                value = days,
                onValueChange = {
                    days=it
                },
                placeholder = { Text("Days") }
            )
            Button(onClick = {viewModel.fetchForecast(key,query,days.toInt())}) {
                Text("Gas")
            }
            if (forecast.isEmpty()) {
                Text("Loading...", fontSize = 20.sp)
            } else {
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(forecast) { hour ->
                        if (hour != null) {
                            ForecastItem(
                                label = hour.time,
                                value = "${hour.temp_c}°C",
                                image = "https:${hour.condition.icon}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastItem(label:String,value:String,image:String){
    Card(modifier = Modifier
        .padding(16.dp)
        .size(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ){
            Row {
                Image(painter = rememberAsyncImagePainter(image),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(label)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(value, fontSize = 20.sp, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenPreview() {
    WeatherAppTheme {
        val navController= rememberNavController()
        ForecastScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastItemPreview() {
    WeatherAppTheme {
        ForecastItem("Home","Home","https://cdn.weatherapi.com/weather/64x64/day/116.png")
    }
}