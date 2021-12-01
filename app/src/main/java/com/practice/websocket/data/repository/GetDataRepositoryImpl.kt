package com.practice.websocket.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketCoinbase
import com.practice.websocket.data.repository.remote.GetDataFromWebSocketIndodax
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetDataRepositoryImpl @Inject constructor(
    private val coinbase: GetDataFromWebSocketCoinbase,
    private val indodax: GetDataFromWebSocketIndodax
) : GetDataRepository {

    override fun initConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                coinbase.initWebSocket()
            }
            launch {
                indodax.initWebSocket()
            }
        }
    }

    override fun closeConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                coinbase.closeConnection()
            }
            launch {
                indodax.closeConnection()
            }
        }
    }

    override fun getDataCoinbase(): LiveData<String> {
        return Transformations.map(coinbase.getPriceData()) {
            it.price ?: "null"
        }
    }

    override fun getDataIndodax(): LiveData<String> {
        return Transformations.map(indodax.getPriceData()) {
            it?.result?.data?.data?.get(0)?.get(2)
        }
    }
}