package com.practice.websocket.data.repository

import androidx.lifecycle.LiveData

interface GetDataRepository {
    fun initConnection()
    fun closeConnection()

    fun getDataCoinbase(): LiveData<String>
    fun getDataIndodax(): LiveData<String>
}