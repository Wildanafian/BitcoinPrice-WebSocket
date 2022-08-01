package com.practice.websocket.domain

import com.practice.websocket.data.repository.Repository
import javax.inject.Inject

class MainActivityUseCaseImpl @Inject constructor(private val repository: Repository) :
    MainActivityUseCase {

    override fun getDataCoinbase() = repository.getDataCoinbase()


    override fun getDataIndodax() = repository.getDataIndodax()


    override fun closeConnection() = repository.closeConnection()
}