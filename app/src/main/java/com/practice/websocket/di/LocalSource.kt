package com.practice.websocket.di

import com.practice.websocket.data.repository.local.LocalSource
import com.practice.websocket.data.repository.local.LocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalSource {

    @Binds
    fun provideLocalSource(localSourceImpl: LocalSourceImpl): LocalSource

}