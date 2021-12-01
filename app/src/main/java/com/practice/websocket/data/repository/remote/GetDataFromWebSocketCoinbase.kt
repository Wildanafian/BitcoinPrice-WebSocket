package com.practice.websocket.data.repository.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.CoinbasePoint
import com.practice.websocket.constant.AllYouNeed.CoinbasePref
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.data.model.ResponseCoinbase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

class GetDataFromWebSocketCoinbase @Inject constructor(private val sharedPreference: SharedPreferences) {
    private lateinit var webSocket: WebSocketClient
    private var priceData = MutableLiveData<ResponseCoinbase>().apply { postValue(Gson().fromJson(sharedPreference.getString(CoinbasePref, null), ResponseCoinbase::class.java) ?: ResponseCoinbase()) }

    fun initWebSocket() {
        createWebSocketClient()
        webSocket.setSocketFactory(SSLSocketFactory.getDefault() as SSLSocketFactory)
        webSocket.connect()
    }

    private fun createWebSocketClient() {
        webSocket = object : WebSocketClient(URI(CoinbasePoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Coinbase")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                val data = Gson().fromJson(message, ResponseCoinbase::class.java)
                if (data.type == "ticker") priceData.postValue(
                    Gson().fromJson(
                        message,
                        ResponseCoinbase::class.java
                    )
                )
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                unSubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.localizedMessage}")
            }
        }
    }

    private fun subscribe() {
        webSocket.send(
            "{" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-USD\"] }]\n" +
                    "}"
        )
    }

    private fun unSubscribe() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                with(sharedPreference.edit()) {
                    putString(CoinbasePref, Gson().toJson(priceData.value))
                    apply()
                }
            }
            launch {
                webSocket.send(
                    "{" +
                            "    \"type\": \"unsubscribe\",\n" +
                            "    \"channels\": [\"ticker\"]\n" +
                            "}"
                )
            }
        }
    }

    fun getPriceData(): MutableLiveData<ResponseCoinbase> {
        return priceData
    }

    fun closeConnection() {
        unSubscribe()
    }
}