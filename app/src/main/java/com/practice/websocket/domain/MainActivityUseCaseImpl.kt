package com.practice.websocket.domain

import androidx.lifecycle.LiveData
import com.practice.websocket.data.repository.GetDataRepository
import javax.inject.Inject

class MainActivityUseCaseImpl @Inject constructor(private val repository: GetDataRepository) :
    MainActivityUseCase {

    override fun initConnection() {
        repository.initConnection()
    }

    override fun closeConnection() {
        repository.closeConnection()
    }

    override fun getDataCoinbase(): LiveData<String> {
        return repository.getDataCoinbase()
    }

    override fun getDataIndodax(): LiveData<String> {
        return repository.getDataIndodax()
    }
}