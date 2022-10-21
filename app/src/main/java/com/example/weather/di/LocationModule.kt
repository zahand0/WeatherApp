package com.example.weather.di

import com.example.weather.data.location.DefaultLocationTracker
import com.example.weather.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}