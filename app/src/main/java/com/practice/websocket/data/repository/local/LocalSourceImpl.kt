package com.practice.websocket.data.repository.local

import android.content.SharedPreferences
import javax.inject.Inject


/**
 * Created by Wildan Nafian on 01/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class LocalSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LocalSource {
    override fun save(key: String, data: String) {
        with(sharedPreferences.edit()) {
            putString(key, data)
            apply()
        }
    }

    override fun getData(key: String): String = sharedPreferences.getString(key, "0") ?: "0"
}