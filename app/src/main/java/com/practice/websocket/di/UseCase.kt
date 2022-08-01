package com.practice.websocket.di

import com.practice.websocket.domain.MainActivityUseCase
import com.practice.websocket.domain.MainActivityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCase {

    @Binds
    fun provideUseCase(useCase: MainActivityUseCaseImpl): MainActivityUseCase

}