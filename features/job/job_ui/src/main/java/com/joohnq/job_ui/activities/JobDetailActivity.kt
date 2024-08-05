package com.joohnq.job_ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.job_ui.databinding.ActivityJobDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailActivity: AppCompatActivity() {
				private lateinit var id: String
				private var _binding: ActivityJobDetailBinding? = null
				private val binding get() = _binding!!

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								_binding = ActivityJobDetailBinding.inflate(layoutInflater)
								setContentView(binding.root)
								binding.setOnApplyWindowInsetsListener()
								id = intent.extras?.getString("id") ?: return
				}
}