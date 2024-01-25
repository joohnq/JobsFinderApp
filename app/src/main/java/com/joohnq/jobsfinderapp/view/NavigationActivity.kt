package com.joohnq.jobsfinderapp.view

import android.Manifest
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityNavigationBinding
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {
    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(
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
    private var hasPermissionGallery = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkAuthentication()
        userViewModel.getUserData()
        solicitPermission()
    }

    private fun solicitPermission() {
        hasPermissionGallery = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

        val listPermissionsDenied = mutableListOf<String>()

        if (!hasPermissionGallery) {
            listPermissionsDenied.add(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
            )
        }

        if(listPermissionsDenied.isNotEmpty()){
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                hasPermissionGallery = permissions[
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.READ_MEDIA_IMAGES
                    } else {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    }
                ] ?: hasPermissionGallery
            }.launch(listPermissionsDenied.toTypedArray())
        }
    }

    private fun checkAuthentication() {
        userViewModel.getUserUid { id ->
            if (id == null) {
                val intent = Intent(this, PresentationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                finish()
            } else {
                initNavigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initNavigation() {
        val graphInflater = navController.navInflater
        val navigationGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navController.graph = navigationGraph
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

}