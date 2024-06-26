package com.joohnq.jobsfinderapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityMainBinding
import com.joohnq.jobsfinderapp.view.components.setOnApplyWindowInsetsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val navController by lazy {
        val navHostFragment =supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController
    }
    private val navigation by lazy {
        Navigation(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setOnApplyWindowInsetsListener()
        navigation.initNavigation(R.id.onboarding)
    }
}