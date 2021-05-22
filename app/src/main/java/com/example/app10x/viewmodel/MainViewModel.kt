package com.example.app10x.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app10x.dataModel.CurrentTemperature
import com.example.app10x.model.repository.MainRepository
import com.example.app10x.utility.NetworkHelper
import com.example.app10x.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository?,
    private val networkHelper: NetworkHelper?
) : ViewModel() {

    private val currentTemperature = MutableLiveData<Resource<CurrentTemperature>>()
    private val forecast = MutableLiveData<Resource<CurrentTemperature>>()

    fun getCurrentTemperature(): LiveData<Resource<CurrentTemperature>> {
        return currentTemperature
    }

    fun getForecast(): LiveData<Resource<CurrentTemperature>> {
        return forecast
    }

    init {
        fetchCurrentTemperature()
        fetchForecast()
    }

    fun fetchCurrentTemperature() {
        viewModelScope.launch {
            currentTemperature.postValue(Resource.loading(null))
            if (networkHelper?.isNetworkConnected() == true) {
                mainRepository?.getCurrent()?.let {
                    if (it.isSuccessful) {
                        currentTemperature.postValue(Resource.success((it.body() as CurrentTemperature)))
                    } else currentTemperature.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else {
                currentTemperature.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun fetchForecast() {
        viewModelScope.launch {
            forecast.postValue(Resource.loading(null))
            if (networkHelper?.isNetworkConnected() == true) {
                mainRepository?.getWeather()?.let {
                    if (it.isSuccessful) {
                        forecast.postValue(Resource.success((it.body() as CurrentTemperature)))
                    } else forecast.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else {
                forecast.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}