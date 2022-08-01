package com.practice.websocket.data.repository

import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getDataCoinbase(): Flow<String>

    fun getDataIndodax(): Flow<String>

    fun closeConnection()
}
