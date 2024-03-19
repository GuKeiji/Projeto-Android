package br.com.greenview

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import br.com.greenview.model.Data
import android.os.Bundle
import android.util.Log
import br.com.greenview.model.AirQuality
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import br.com.greenview.service.RetrofitFactory
import br.com.greenview.ui.theme.GreenViewTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            GreenViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    Tela()
                }
            }
        }
    }
}


@Composable
fun Tela() {

    var airQualityState = remember {
        mutableStateOf(AirQuality().data.aqi)
    }

    Log.i("FIAP", "antes airQuality.aqi: ${airQualityState.value}")


    //Request api
    var request = RetrofitFactory().listarAirQuality().listarAirQuality(token = "5c01a515c860db94c1ccad122d967ca61b41d72b")

    request.enqueue(object: Callback<AirQuality>{
        override fun onResponse(call: Call<AirQuality>, response: Response<AirQuality>) {
            airQualityState.value = response.body()!!.data.aqi
            Log.i("FIAP", "onResponse airQuality.aqi: ${airQualityState.value}")
            Log.i("FIAP", "onResponse: ${response.body()}")
            Log.i("FIAP", "onResponse: ${response.code()}")

        }

        override fun onFailure(call: Call<AirQuality>, t: Throwable) {
            Log.e("FIAP", "onFailure: ${t.message}")
        }

    })

    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#858585")))
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Top,
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
            colors = CardDefaults.cardColors(Color.Blue)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "São Paulo",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${airQualityState.value}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 51.sp
                )
                Text(
                    text = "Seguro",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = "Pode sair de casa",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPreview() {
    GreenViewTheme {
        Tela(currentLocation = LatLng())
    }
}
*/