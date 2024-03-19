package br.com.greenview.service

import br.com.greenview.model.AirQuality
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirQualityService {

    @GET("feed/here")
    fun listarAirQuality(@Query("token") token: String): Call<AirQuality>
}