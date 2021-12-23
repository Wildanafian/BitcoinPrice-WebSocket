package com.practice.websocket.di

import com.practice.websocket.data.repository.GetDataRepository
import com.practice.websocket.data.repository.GetDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Repository {

    @Binds
    fun provideRepository(repository: GetDataRepositoryImpl) : GetDataRepository

}