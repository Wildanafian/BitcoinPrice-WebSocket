package com.practice.websocket.data.repository

import com.practice.websocket.constant.AllYouNeed.CoinbasePref
import com.practice.websocket.constant.AllYouNeed.IndodaxPref
import com.practice.websocket.data.model.ResponseCoinbase
import com.practice.websocket.data.model.ResponseIndodax
import com.practice.websocket.data.repository.local.LocalSource
import com.practice.websocket.data.repository.remote.WebSocketSource
import com.practice.websocket.di.Coinbase
import com.practice.websocket.di.Indodax
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @Coinbase private val coinbase: WebSocketSource<ResponseCoinbase>,
    @Indodax private val indodax: WebSocketSource<ResponseIndodax>,
    private val localSource: LocalSource
) : Repository {

    override fun getDataCoinbase() = flow {
        coinbase.getDataFromWebsocket()
            .collect {
                val data = it.price ?: "0"
                emit(data)
                localSource.save(CoinbasePref, data)
            }
    }
        .onStart {
            emit(localSource.getData(CoinbasePref))
        }
        .catch {
            emit(localSource.getData(CoinbasePref))
        }
        .flowOn(Dispatchers.IO)

    override fun getDataIndodax() = flow {
        indodax.getDataFromWebsocket()
            .collect {
                val data = it.result?.data?.data?.get(0)?.get(2) ?: "0"
                emit(data)
                localSource.save(IndodaxPref, data)
            }
    }
        .onStart {
            emit(localSource.getData(IndodaxPref))
        }
        .catch {
            emit(localSource.getData(IndodaxPref))
        }
        .flowOn(Dispatchers.IO)

    override fun closeConnection() {
        coinbase.closeConnection()
        indodax.closeConnection()
    }
}