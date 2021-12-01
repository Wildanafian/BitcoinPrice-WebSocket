package com.practice.websocket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.websocket.domain.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val useCase: MainActivityUseCase) : ViewModel() {

    fun initConnection() {
        viewModelScope.launch {
            useCase.initConnection()
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            useCase.closeConnection()
        }
    }

    fun getDataStreamCoinbase(): LiveData<String> {
        return Transformations.map(useCase.getDataCoinbase()) {
            if (it == null || it == "null") "Searching Data..."
            else "Coinbase : $it USD"
        }
    }

    fun getDataStreamIndodax(): LiveData<String> {
        return Transformations.map(useCase.getDataIndodax()) {
            if (it == null || it == "null") "Searching Data..."
            else "Indodax : Rp $it"
        }
    }

}