package com.example.weather.data.repository

import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.weather.data.mappers.toWeatherInfo
import com.example.weather.data.remote.WeatherApi
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.util.Resource
import com.example.weather.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val geocoder: Geocoder
) : WeatherRepository {

    companion object {
        private const val TAG = "WeatherRepositoryImpl"
    }

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        Log.d(TAG, "getWeatherData: We're in")
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            Log.e(TAG, "error during loading data from api", e )
            Resource.Error(e.message ?:"An unknown error occurred.")
        }
    }

    override suspend fun getLocality(lat: Double, long: Double): Resource<String> {
        return try {
            var addressString = ""
            val addressList: MutableList<Address>? = geocoder.getFromLocation(lat, long, 1)
            if (addressList != null && addressList.isNotEmpty()) {
                val address = addressList[0]
                addressString = address.locality
            }
            Log.d(TAG, "getCurrentPlace: $addressString")
            Resource.Success(addressString)
        } catch (e: Exception) {
            Resource.Error(e.message ?:"Couldn't get current place name.")
        }
    }
}