package com.joohnq.jobsfinderapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityPresentationBinding
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentationActivity : AppCompatActivity() {
    private val binding: ActivityPresentationBinding by lazy {
        ActivityPresentationBinding.inflate(
            layoutInflater
        )
    }
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment

        navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getUserUid() { id ->
            if (id != null) {
                val intent = Intent(this, NavigationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
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