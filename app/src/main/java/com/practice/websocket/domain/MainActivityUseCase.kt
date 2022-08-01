package com.practice.websocket.domain

import kotlinx.coroutines.flow.Flow

interface MainActivityUseCase {

    fun getDataCoinbase(): Flow<String>

    fun getDataIndodax(): Flow<String>

    fun closeConnection()
}