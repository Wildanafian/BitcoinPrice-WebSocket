package com.practice.websocket.di

import com.practice.websocket.data.repository.Repository
import com.practice.websocket.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Repository {

    @Binds
    fun provideRepository(repository: RepositoryImpl): Repository

}