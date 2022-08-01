package com.practice.websocket.data.repository.local


/**
 * Created by Wildan Nafian on 01/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
interface LocalSource {

    fun save(key: String, data: String)

    fun getData(key: String): String
}