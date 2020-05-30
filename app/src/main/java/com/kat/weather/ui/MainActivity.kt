package com.kat.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kat.weather.ui.main.MainFragment
import com.kat.weather.ui.main.MainViewModel

/**
 * Activity to display weather details
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        init()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment()).commit()
    }

    /**
     * Initialization of objects
     */
    private fun init() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.isDataAvailabile = false
    }
}
