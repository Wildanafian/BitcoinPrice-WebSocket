package com.practice.websocket.data.repository.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.IndodaxPoint
import com.practice.websocket.constant.AllYouNeed.IndodaxPref
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.data.model.ResponseIndodax
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

class GetDataFromWebSocketIndodax @Inject constructor(private val sharedPreference: SharedPreferences) {
    private lateinit var webSocket: WebSocketClient
    private var priceData = MutableLiveData<ResponseIndodax>().apply { postValue(Gson().fromJson(sharedPreference.getString(IndodaxPref, null), ResponseIndodax::class.java) ?: ResponseIndodax()) }

    fun initWebSocket() {
        createWebSocketClient()
        webSocket.setSocketFactory(SSLSocketFactory.getDefault() as SSLSocketFactory)
        webSocket.connect()
    }

    private fun createWebSocketClient() {
        webSocket = object : WebSocketClient(URI(IndodaxPoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Indodax")
                subscribeAuth()
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage indodax: $message")
                val data = Gson().fromJson(message, ResponseIndodax::class.java)
                if (data.id == null) priceData.postValue(data)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                unSubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.localizedMessage}")
            }
        }
    }

    private fun subscribeAuth() {
        webSocket.send(
            "{\n" +
                    "    \"params\": {\n" +
                    "        \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE5NDY2MTg0MTV9.UR1lBM6Eqh0yWz-PVirw1uPCxe60FdchR8eNVdsskeo\"\n" +
                    "    },\n" +
                    "    \"id\": 1\n" +
                    "}\n"
        )
    }

    private fun subscribe() {
        webSocket.send(
            "{\n" +
                    "    \"method\": 1,\n" +
                    "    \"params\": {\n" +
                    "        \"channel\": \"chart:tick-btcidr\",\n" +
                    "        \"recover\": true,\n" +
                    "        \"offset\": 820574\n" +
                    "    },\n" +
                    "    \"id\": 2\n" +
                    "}"
        )
    }

    private fun unSubscribe() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                with(sharedPreference.edit()) {
                    putString(IndodaxPref, Gson().toJson(priceData.value))
                    apply()
                }
            }
            launch {
                webSocket.send(
                    "{\n" +
                            "    \"method\": 2,\n" +
                            "    \"params\": {\n" +
                            "        \"channel\": \"chart:tick-btcidr\"\n" +
                            "    },\n" +
                            "    \"id\": 3\n" +
                            "}"
                )
            }
        }
    }

    fun getPriceData(): MutableLiveData<ResponseIndodax> {
        return priceData
    }

    fun closeConnection() {
        unSubscribe()
    }
}