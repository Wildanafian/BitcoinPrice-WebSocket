package com.practice.websocket.di

import com.practice.websocket.data.model.ResponseCoinbase
import com.practice.websocket.data.model.ResponseIndodax
import com.practice.websocket.data.repository.remote.CoinbaseSource
import com.practice.websocket.data.repository.remote.IndodaxSource
import com.practice.websocket.data.repository.remote.WebSocketSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSource {

    @Binds
    @Coinbase
    fun provideRemoteCoinbase(coinbaseSource: CoinbaseSource): WebSocketSource<ResponseCoinbase>

    @Binds
    @Indodax
    fun provideRemoteIndodax(indodaxSource: IndodaxSource): WebSocketSource<ResponseIndodax>

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Coinbase

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Indodax