package com.practice.websocket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.websocket.domain.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val useCase: MainActivityUseCase) :
    ViewModel() {

    fun initConnection() {
        viewModelScope.launch {
            getDataStreamCoinbase()
            getDataStreamIndodax()
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            useCase.closeConnection()
        }
    }

    private val _btcPriceCoinbase = MutableLiveData<String>()
    val btcPriceCoinbase: LiveData<String> = _btcPriceCoinbase
    private fun getDataStreamCoinbase() {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.getDataCoinbase()
                .collect {
                    _btcPriceCoinbase.postValue(it.ifEmpty { "0" }.formatNumber())
                }
        }
    }

    private val _btcPriceIndodax = MutableLiveData<String>()
    val btcPriceIndodax: LiveData<String> = _btcPriceIndodax
    private fun getDataStreamIndodax() {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.getDataIndodax()
                .collect {
                    _btcPriceIndodax.postValue(it.ifEmpty { "0" }.formatNumber())
                }
        }
    }

    private fun String.formatNumber(): String {
        val formater = NumberFormat.getIntegerInstance(Locale.GERMANY)
        formater.maximumFractionDigits = 2
        return formater.format(this.toDouble())
    }

}