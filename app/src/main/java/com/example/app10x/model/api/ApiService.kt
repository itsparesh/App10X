package com.example.app10x.model.api

import com.example.app10x.dataModel.CurrentTemperature
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getCurrentTemperature(): Response<CurrentTemperature>

    @GET("forecast?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getForecast(): Response<CurrentTemperature>

}