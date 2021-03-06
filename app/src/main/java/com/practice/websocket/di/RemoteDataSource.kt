package com.practice.websocket.di

import android.content.SharedPreferences
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketCoinbase
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketIndodax
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSource {

    @Provides
    fun provideRemoteCoinbase(sharedPreference: SharedPreferences) = GetDataFromWebSocketCoinbase(sharedPreference)

    @Provides
    fun provideRemoteIndodax(sharedPreference: SharedPreferences) = GetDataFromWebSocketIndodax(sharedPreference)

}