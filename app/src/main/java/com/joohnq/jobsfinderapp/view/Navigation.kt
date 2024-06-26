package com.joohnq.jobsfinderapp.view

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joohnq.jobsfinderapp.R

class Navigation(private val navController: NavController) {
    fun initNavigation(bottomNav: BottomNavigationView, startDestination: Int = R.id.main) {
        val graphInflater = navController.navInflater
        val navigationGraph = graphInflater.inflate(R.navigation.main_graph)
        navController.graph = navigationGraph
        navigationGraph.setStartDestination(startDestination)
        NavigationUI.setupWithNavController(bottomNav, navController)
    }

    fun initNavigation(startDestination: Int = R.id.onboarding) {
        val graphInflater = navController.navInflater
        val navigationGraph = graphInflater.inflate(R.navigation.main_graph)
        navigationGraph.setStartDestination(startDestination)
        navController.graph = navigationGraph
    }
}