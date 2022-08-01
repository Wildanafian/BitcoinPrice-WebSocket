package com.practice.websocket.data.repository.remote

import android.util.Log
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.IndodaxPoint
import com.practice.websocket.constant.AllYouNeed.SubsAuthIndodax
import com.practice.websocket.constant.AllYouNeed.SubsIndodax
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.constant.AllYouNeed.UnSubsIndodax
import com.practice.websocket.data.model.ResponseIndodax
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject

class IndodaxSource @Inject constructor() : WebSocketSource<ResponseIndodax> {

    private lateinit var webSocket: WebSocketClient

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getDataFromWebsocket() = callbackFlow {
        webSocket = object : WebSocketClient(URI(IndodaxPoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Indodax")
                webSocket.send(SubsAuthIndodax)
                webSocket.send(SubsIndodax)
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage indodax: $message")
                try {
                    val data = Gson().fromJson(message, ResponseIndodax::class.java)
                    if (data.id == null) trySend(data)
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
        runCatching { webSocket.send(UnSubsIndodax) }
    }
}