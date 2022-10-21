package com.example.weather.di

import com.example.weather.data.location.DefaultLocationTracker
import com.example.weather.data.repository.WeatherRepositoryImpl
import com.example.weather.domain.location.LocationTracker
import com.example.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}