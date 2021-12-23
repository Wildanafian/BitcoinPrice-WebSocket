package com.practice.websocket.data.repository.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.practice.websocket.constant.AllYouNeed.IndodaxPoint
import com.practice.websocket.constant.AllYouNeed.IndodaxPref
import com.practice.websocket.constant.AllYouNeed.SubsAuthIndodax
import com.practice.websocket.constant.AllYouNeed.SubsIndodax
import com.practice.websocket.constant.AllYouNeed.TAG
import com.practice.websocket.constant.AllYouNeed.UnSubsIndodax
import com.practice.websocket.data.model.ResponseIndodax
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class GetDataFromWebSocketIndodax (private val sharedPreference: SharedPreferences) {

    private lateinit var webSocket: WebSocketClient
    private var priceData = MutableLiveData<ResponseIndodax>().apply { postValue(Gson().fromJson(sharedPreference.getString(IndodaxPref, null), ResponseIndodax::class.java) ?: ResponseIndodax()) }

    fun initWebSocket() {
        webSocket = object : WebSocketClient(URI(IndodaxPoint)) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: Indodax")
                webSocket.send(SubsAuthIndodax)
                webSocket.send(SubsIndodax)
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage indodax: $message")
                val data = Gson().fromJson(message, ResponseIndodax::class.java)
                if (data.id == null) priceData.postValue(data)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {}

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.localizedMessage}")
            }
        }
        webSocket.connect()
    }

    fun getPriceData(): MutableLiveData<ResponseIndodax> {
        return priceData
    }

    fun closeConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                with(sharedPreference.edit()) {
                    putString(IndodaxPref, Gson().toJson(priceData.value))
                    apply()
                }
            }
            launch {
                if (webSocket.isOpen) webSocket.send(UnSubsIndodax)
            }
        }
    }
}