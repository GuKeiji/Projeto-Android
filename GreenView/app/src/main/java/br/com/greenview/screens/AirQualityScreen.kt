package br.com.greenview.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.greenview.R
import br.com.greenview.model.AirQuality
import br.com.greenview.service.RetrofitFactory
import br.com.greenview.ui.theme.GreenViewTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TelaAirQuality() {
    //Cores indices
    val safeColor = Color(android.graphics.Color.parseColor("#449e48"))
    val moderateColor = Color(android.graphics.Color.parseColor("#ffde33"))
    val almostUnhealthyColor = Color(android.graphics.Color.parseColor("#b79d1b"))
    val unhealthyColor = Color.Red
    val veryUnhealthyColor = Color(android.graphics.Color.parseColor("#660099"))
    val hazardousColor = Color(android.graphics.Color.parseColor("#7e0023"))

    //Indices determinantes
    val safeThreshold = 50
    val moderateThreshold = 100
    val almostUnhealthyThreshold = 150
    val unhealthyThreshold = 200
    val veryUnhealthyThreshold = 300

    var airQualityState = remember {
        mutableStateOf(AirQuality().data.aqi)
    }

    var cidadeState = remember {
        mutableStateOf(AirQuality().data.city)
    }

    //Request api
    var request = RetrofitFactory().listarAirQuality().listarAirQuality(token = "5c01a515c860db94c1ccad122d967ca61b41d72b")

    request.enqueue(object: Callback<AirQuality>{
        override fun onResponse(call: Call<AirQuality>, response: Response<AirQuality>) {
            airQualityState.value = response.body()!!.data.aqi
            cidadeState.value = response.body()!!.data.city
            Log.i("FIAP", "onResponse: ${response.body()}")
            Log.i("FIAP", "onResponse: ${response.code()}")

        }

        override fun onFailure(call: Call<AirQuality>, t: Throwable) {
            Log.e("FIAP", "onFailure: ${t.message}")
        }

    })

    val fontCardColor = when {
        airQualityState.value in 52..moderateThreshold -> Color.Black
        else -> Color.White
    }

    val cardColor = when {
        airQualityState.value <= safeThreshold -> safeColor
        airQualityState.value <= moderateThreshold -> moderateColor
        airQualityState.value <= almostUnhealthyThreshold -> almostUnhealthyColor
        airQualityState.value <= unhealthyThreshold -> unhealthyColor
        airQualityState.value <= veryUnhealthyThreshold -> veryUnhealthyColor
        airQualityState.value > veryUnhealthyThreshold -> hazardousColor
        else -> hazardousColor
    }

    val tituloIndice = when {
        airQualityState.value <= safeThreshold -> "Bom"
        airQualityState.value <= moderateThreshold -> "Moderado"
        airQualityState.value <= almostUnhealthyThreshold -> "Um pouco Prejudicial"
        airQualityState.value <= unhealthyThreshold -> "Prejudicial"
        airQualityState.value <= veryUnhealthyThreshold -> "Muito Prejudicial"
        airQualityState.value > veryUnhealthyThreshold -> "Perigoso"
        else -> "Bom"
    }

    val descIndice = when {
        airQualityState.value <= safeThreshold -> "Qualidade do ar satisfatória, sem riscos"
        airQualityState.value <= moderateThreshold -> "Qualidade do ar aceitável, pessoas com sensibilidade devem evitar estas áreas"
        airQualityState.value <= almostUnhealthyThreshold -> "Pessoas com sensibilidade devem evitar estas áreas"
        airQualityState.value <= unhealthyThreshold -> "Afeta a saúde de todas as pessoas, prejudicial à saúde"
        airQualityState.value <= veryUnhealthyThreshold -> "Condição de emergência, toda a população é afetada"
        airQualityState.value > veryUnhealthyThreshold -> "Alerta à saúde, desencadeamento de doenças e saúde gravemente afetada"
        else -> "Seguro"
    }
    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#000000")).copy(alpha = 0.8f))
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Qualidade do ar",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(15.dp),
            colors = CardDefaults.cardColors(cardColor)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${cidadeState.value.name}",
                    color = fontCardColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${airQualityState.value}",
                    color = fontCardColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 51.sp
                )
                Text(
                    text = "${tituloIndice}",
                    color = fontCardColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = "${descIndice}",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPreview() {
    GreenViewTheme {
        TelaAirQuality()
    }
}