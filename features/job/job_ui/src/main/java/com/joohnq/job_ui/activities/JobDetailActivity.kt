package com.joohnq.job_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import com.joohnq.core.BaseActivity
import com.joohnq.core.constants.Constants
import com.joohnq.core.getParcelableExtraProvider
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.ActivityJobDetailBinding
import com.joohnq.job_ui.navigation.JobDetailNavigationImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailActivity: BaseActivity<ActivityJobDetailBinding>() {
				private var job: Job? = null
				private val onFailure = { error: String? ->
								SnackBarHelper(binding.root, error.toString())
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityJobDetailBinding =
								ActivityJobDetailBinding.inflate(layoutInflater)

				private fun ActivityJobDetailBinding.initButtons() {
								onPressBack = View.OnClickListener { finish() }

								onApplyJob = View.OnClickListener {
												job?.run {
																val jobUrl: String? =
																				if (externalApplyLink.isNullOrEmpty()) url else externalApplyLink
																if (jobUrl.isNullOrEmpty()) {
																				onFailure("Erro")
																				return@run
																}
																try {
																				JobDetailNavigationImpl.navigateToWebLink(this@JobDetailActivity, jobUrl)
																} catch (e: Exception) {
																				onFailure(e.message)
																}
												}
								}
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								job = intent.getParcelableExtraProvider(Constants.PARAMETER_JOB)
								job?.let { binding.job = job }
								binding.initButtons()
				}
}