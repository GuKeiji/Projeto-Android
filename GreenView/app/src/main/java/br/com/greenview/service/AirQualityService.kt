package br.com.greenview.service

import br.com.greenview.model.AirQuality
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AirQualityService {

    @GET("//feed/geo:{lat};{lng}/?token={token}")
    fun listarAirQuality(@Path("lat") lat: Float, @Path("lng") lng: Float, @Path("token") token: String): Call<AirQuality>
}