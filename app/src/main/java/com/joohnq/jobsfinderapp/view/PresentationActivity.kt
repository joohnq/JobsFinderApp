package com.joohnq.jobsfinderapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityPresentationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentationActivity : AppCompatActivity() {
    private var _binding: ActivityPresentationBinding? = null
    private val binding get() = _binding!!
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
        _binding = ActivityPresentationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation.initNavigation()
    }
}