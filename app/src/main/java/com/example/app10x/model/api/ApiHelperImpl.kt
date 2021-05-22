package com.example.app10x.model.api

import com.example.app10x.dataModel.CurrentTemperature
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService?) : ApiHelper {
    override suspend fun getCurrent(): Response<CurrentTemperature>? =
        apiService?.getCurrentTemperature()


    override suspend fun getWeather(): Response<CurrentTemperature>? =
        apiService?.getForecast()

}