package com.joohnq.jobsfinderapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityLoadingBinding
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.view.components.setOnApplyWindowInsetsListener
import com.joohnq.jobsfinderapp.view.fragments.LoginFragment
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {
    private var _binding: ActivityLoadingBinding? = null
    private val binding get() = _binding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setOnApplyWindowInsetsListener()
        userViewModel.getUser()
        observers()
    }

    private fun observers() {
        userViewModel.user.observe(this) { state ->
            state.handleUiState(
                onSuccess = { user ->
                    val route =
                        if (user == null) PresentationActivity::class.java else MainActivity::class.java

                    createIntent(route)
                },
                onFailure = {
                    createIntent(PresentationActivity::class.java)
                }
            )
        }
    }

    fun createIntent(route: Class<*>) {
        Intent(this, route)
            .also {
                startActivity(it)
                finish()
            }
    }
}