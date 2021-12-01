package com.practice.websocket.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.practice.websocket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        reportFullyDrawn()
        super.onCreate(savedInstanceState)
        with(bind) {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            data = viewModel
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initConnection()
    }

    override fun onPause() {
        super.onPause()
        viewModel.closeConnection()
    }
}