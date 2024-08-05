package com.joohnq.profile.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.profile.databinding.ActivityProfileDetailBinding
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDetailActivity: AppCompatActivity() {
				private var _binding: ActivityProfileDetailBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by viewModels()

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								_binding = ActivityProfileDetailBinding.inflate(layoutInflater)
								setContentView(binding.root)
								binding.setOnApplyWindowInsetsListener()
								binding.viewmodel = userViewModel
				}
}