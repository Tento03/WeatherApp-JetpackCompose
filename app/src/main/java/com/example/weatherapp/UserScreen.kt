package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.ui.theme.BlueJC
import com.example.weatherapp.ui.theme.DarkBlueJC
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.WeatherViewModel

@Composable
fun WeatherScreen(viewModel:WeatherViewModel= hiltViewModel()){
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey="3fb96a0af1ba4680bb683923251502"

   Box(modifier = Modifier
       .padding(16.dp)
       .fillMaxSize()
       .paint(
           painterResource(id = R.drawable.img),
           contentScale = ContentScale.FillBounds
       )){
           Column (modifier = Modifier
               .fillMaxSize()
               .padding(16.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Top
           ) {
               Spacer(modifier = Modifier.height(180.dp))
               OutlinedTextField(
                   value = city,
                   onValueChange = { newText->
                       city=newText
                   },
                   placeholder = { Text("City") },
                   modifier = Modifier.fillMaxWidth(),
                   shape = RoundedCornerShape(30.dp),
                   colors = TextFieldDefaults.colors(
                       focusedContainerColor = Color.White,
                       unfocusedLabelColor = Color.White,
                       unfocusedIndicatorColor = BlueJC,
                       focusedIndicatorColor = BlueJC,
                       focusedLabelColor = BlueJC
                   )
               )
               Spacer(modifier = Modifier.padding(16.dp))
               Button(onClick = {viewModel.fetchWeather(apiKey,city)}, colors = ButtonDefaults.buttonColors(
                   BlueJC)) {
                   Text("Check Weather")
               }
               Spacer(modifier = Modifier.height(16.dp))
               weatherData?.let {
                   Row (modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceEvenly) {
                       WeatherCard(label = "City", value = it.location.name.toString(), icon = Icons.Default.Place)
                       WeatherCard(label = "Temperature", value = it.current.temp_c.toString(), icon = Icons.Default.Star)
                   }
                   Row (modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceEvenly) {
                       WeatherCard(label = "Humidity", value = it.current.humidity.toString(), icon = Icons.Default.Warning)
                       WeatherCard(label = "Description", value = it.current.condition.text, icon = Icons.Default.Info)
                   }
               }
           }
       }
}

@Composable
fun WeatherCard(label:String, value:String, icon: ImageVector){
    Card(modifier = Modifier
        .padding(8.dp)
        .size(150.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
                Icon(imageVector = icon,contentDescription = null,
                    tint = DarkBlueJC,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label, fontSize = 14.sp, color = DarkBlueJC)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center){
                Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = BlueJC)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WeatherScreenPreview(){
    WeatherAppTheme {
        WeatherScreen()
    }
}