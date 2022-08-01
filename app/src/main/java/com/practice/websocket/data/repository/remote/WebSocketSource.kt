package com.practice.websocket.data.repository.remote

import kotlinx.coroutines.flow.Flow


/**
 * Created by Wildan Nafian on 01/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
interface WebSocketSource<T> {

    fun getDataFromWebsocket(): Flow<T>

    fun closeConnection()
}