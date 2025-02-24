package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.weather.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember { mutableStateOf("") }
    val key = "3fb96a0af1ba4680bb683923251502"

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.img),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text("City") }
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = { viewModel.fetchWeather(key, city) }) {
                Text("Fetch")
            }

            weatherData?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(
                        label = "City",
                        value = it.location.name,
                        image = "https:" + it.current.condition.icon
                    )
                    WeatherCard(
                        label = "Temperature",
                        value = "${it.current.temp_c}°C",
                        image = "https:" + it.current.condition.icon
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(
                        label = "Humidity",
                        value = "${it.current.humidity}%",
                        image = "https:" + it.current.condition.icon
                    )
                    WeatherCard(
                        label = "Description",
                        value = it.current.condition.text,
                        image = "https:" + it.current.condition.icon
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String, image: String) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .size(150.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Load image using Coil's rememberAsyncImagePainter
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = label, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Preview for WeatherCard
@Preview(showBackground = true)
@Composable
private fun WeatherCardPreview() {
    WeatherCard(
        label = "Temperature",
        value = "25°C",
        image = "https://cdn.weatherapi.com/weather/64x64/day/116.png"
    )
}

// Preview for WeatherScreen (static data)
@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    Column {
        WeatherScreenPreviewContent()
    }
}

@Composable
private fun WeatherScreenPreviewContent() {
    WeatherCard(
        label = "City",
        value = "Jakarta",
        image = "https://cdn.weatherapi.com/weather/64x64/day/116.png"
    )
}