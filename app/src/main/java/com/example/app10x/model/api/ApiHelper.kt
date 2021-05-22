package com.example.app10x.model.api

import com.example.app10x.dataModel.CurrentTemperature
import retrofit2.Response

interface ApiHelper {

    suspend fun getCurrent(): Response<CurrentTemperature>?
    suspend fun getWeather(): Response<CurrentTemperature>?

}