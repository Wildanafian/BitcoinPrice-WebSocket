package com.practice.websocket.data.repository.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.CoinbasePoint
import com.practice.websocket.constant.AllYouNeed.CoinbasePref
import com.practice.websocket.constant.AllYouNeed.SubsCoinbase
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.constant.AllYouNeed.UnSubsCoinbase
import com.practice.websocket.data.model.ResponseCoinbase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class GetDataFromWebSocketCoinbase (private val sharedPreference: SharedPreferences) {

    private lateinit var webSocket: WebSocketClient
    private var priceData = MutableLiveData<ResponseCoinbase>().apply { postValue(Gson().fromJson(sharedPreference.getString(CoinbasePref, null), ResponseCoinbase::class.java) ?: ResponseCoinbase()) }

    fun initWebSocket() {
        webSocket = object : WebSocketClient(URI(CoinbasePoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Coinbase")
                webSocket.send(SubsCoinbase)
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                val data = Gson().fromJson(message, ResponseCoinbase::class.java)
                if (data.type == "ticker") priceData.postValue(Gson().fromJson(message, ResponseCoinbase::class.java))
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {}

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.localizedMessage}")
            }
        }
        webSocket.connect()
    }

    fun getPriceData(): MutableLiveData<ResponseCoinbase> {
        return priceData
    }

    fun closeConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                with(sharedPreference.edit()) {
                    putString(CoinbasePref, Gson().toJson(priceData.value))
                    apply()
                }
            }
            launch {
                if (webSocket.isOpen) webSocket.send(UnSubsCoinbase)
            }
        }
    }
}