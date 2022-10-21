package com.example.weather.data.mappers

import com.example.weather.data.remote.WeatherDataDto
import com.example.weather.data.remote.WeatherDto
import com.example.weather.domain.weather.WeatherData
import com.example.weather.domain.weather.WeatherInfo
import com.example.weather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                weatherType = WeatherType.fromWMO(weatherCode),
                windSpeed = windSpeed,
                pressure = pressure,
                humidity = humidity.roundToInt()
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val hour = if (now.minute < 30) now.hour else now.hour + 1
    val day = if (hour < 24) 0 else 1
    val currentWeatherData = weatherDataMap[day]?.find {
        it.time.hour == hour % 24
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}