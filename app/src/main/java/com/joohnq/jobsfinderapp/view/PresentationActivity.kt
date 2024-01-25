package com.joohnq.jobsfinderapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityPresentationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentationActivity : AppCompatActivity() {
    private val binding: ActivityPresentationBinding by lazy {
        ActivityPresentationBinding.inflate(
            layoutInflater
        )
    }
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment

        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val graphInflater = navController.navInflater
        val presentationGraph = graphInflater.inflate(R.navigation.presentation_graph)
        navController.graph = presentationGraph
    }
}