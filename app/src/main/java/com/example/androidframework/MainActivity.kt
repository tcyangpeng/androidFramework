package com.example.androidframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidframework.databinding.ActivityMainBinding
import com.example.feature.weather.ui.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity that serves as the entry point of the app.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WeatherFragment.newInstance())
                .commit()
        }
    }
}
