package com.joohnq.job_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import com.joohnq.core.BaseActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.job_ui.databinding.ActivityJobDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailActivity: BaseActivity<ActivityJobDetailBinding>() {
				private lateinit var id: String

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityJobDetailBinding =
								ActivityJobDetailBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								id = intent.extras?.getString("id") ?: return
				}
}