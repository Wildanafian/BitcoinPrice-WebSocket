package com.practice.websocket.data.repository.remote

import android.util.Log
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.CoinbasePoint
import com.practice.websocket.constant.AllYouNeed.SubsCoinbase
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.constant.AllYouNeed.UnSubsCoinbase
import com.practice.websocket.data.model.ResponseCoinbase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject

class CoinbaseSource @Inject constructor() : WebSocketSource<ResponseCoinbase> {

    private lateinit var webSocket: WebSocketClient

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getDataFromWebsocket() = callbackFlow {
        webSocket = object : WebSocketClient(URI(CoinbasePoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Coinbase")
                webSocket.send(SubsCoinbase)
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                try {
                    if (message?.contains("ticker") == true) {
                        val data = Gson().fromJson(message, ResponseCoinbase::class.java)
                        trySend(data)
                    }
                } catch (e: Exception) {
                    close()
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {}

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.localizedMessage}")
            }
        }
        webSocket.connect()
        awaitClose { closeConnection() }
    }

    override fun closeConnection() {
        runCatching { webSocket.send(UnSubsCoinbase) }
    }
}