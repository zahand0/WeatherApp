package com.example.weather.domain.util

import com.example.weather.domain.weather.WeatherData
import com.example.weather.domain.weather.WeatherInfo
import com.example.weather.domain.weather.WeatherType
import java.time.LocalDateTime

val weatherDataExample = WeatherData(
    time = LocalDateTime.now(),
    temperatureCelsius = 5.2,
    pressure = 1024.0,
    windSpeed = 6.0,
    humidity = 69,
    weatherType = WeatherType.HeavyRain
)


val weatherInfoExample = WeatherInfo(
    weatherDataPerDay = mapOf(0 to listOf(
        weatherDataExample,
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(1)
        ),
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(2),
            temperatureCelsius = 8.3,
            weatherType = WeatherType.Foggy
        ),
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(3),
            temperatureCelsius = 9.3,
            weatherType = WeatherType.ClearSky
        ),
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(4),
            temperatureCelsius = 10.3,
            weatherType = WeatherType.LightDrizzle
        ),
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(5),
            temperatureCelsius = 11.3,
            weatherType = WeatherType.SnowGrains
        ),
        weatherDataExample.copy(
            time = weatherDataExample.time.plusHours(6),
            temperatureCelsius = 12.3,
            weatherType = WeatherType.ModerateThunderstorm
        )
    )),
    currentWeatherData = weatherDataExample
)