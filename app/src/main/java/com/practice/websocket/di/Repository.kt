package com.practice.websocket.di

import com.practice.websocket.data.repository.GetDataRepository
import com.practice.websocket.data.repository.GetDataRepositoryImpl
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketCoinbase
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketIndodax
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Repository {

    @Singleton
    @Provides
    fun provideRepository(coinbase: GetDataFromWebSocketCoinbase, indodax: GetDataFromWebSocketIndodax) : GetDataRepository{
        return GetDataRepositoryImpl(coinbase, indodax)
    }

}