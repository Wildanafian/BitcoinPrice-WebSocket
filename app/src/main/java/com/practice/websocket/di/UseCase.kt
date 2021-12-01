package com.practice.websocket.di

import com.practice.websocket.data.repository.GetDataRepository
import com.practice.websocket.domain.MainActivityUseCase
import com.practice.websocket.domain.MainActivityUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCase {

    @Singleton
    @Provides
    fun provideUseCase(repository: GetDataRepository) : MainActivityUseCase{
        return MainActivityUseCaseImpl(repository)
    }

}