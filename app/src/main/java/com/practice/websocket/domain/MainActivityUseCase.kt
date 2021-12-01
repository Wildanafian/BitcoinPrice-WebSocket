package com.practice.websocket.domain

import androidx.lifecycle.LiveData

interface MainActivityUseCase {
    fun initConnection()
    fun closeConnection()

    fun getDataCoinbase(): LiveData<String>
    fun getDataIndodax(): LiveData<String>
}