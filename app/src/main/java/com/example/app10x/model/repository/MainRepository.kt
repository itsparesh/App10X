package com.example.app10x.model.repository

import com.example.app10x.model.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper?) {

    suspend fun getCurrent() = apiHelper?.getCurrent()
    suspend fun getWeather() = apiHelper?.getWeather()

}
