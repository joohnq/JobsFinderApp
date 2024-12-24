package com.joohnq.job_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.joohnq.domain.constant.Constants
import com.joohnq.domain.entity.Job
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job.ui.databinding.ActivityJobDetailBinding
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.getParcelableExtraProvider
import com.joohnq.ui.setOnApplyWindowInsetsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailActivity: BaseActivity<ActivityJobDetailBinding>() {
				private var job: Job? = null
				private val favoritesViewModel: FavoritesViewModel by viewModels<FavoritesViewModel>()
				private val onFailure = { error: String? ->
								com.joohnq.ui.helper.SnackBarHelper(binding.root, error.toString())
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityJobDetailBinding =
								ActivityJobDetailBinding.inflate(layoutInflater)

				private fun ActivityJobDetailBinding.initButtons() {
//								onPressBack = View.OnClickListener { finish() }
//								onPressFavorite = View.OnClickListener {
//												val newState = !(binding.isFavorited)!!
//												binding.isFavorited = newState
//												job?.run {
//																favoritesViewModel.toggle(job!!.id)
//												}
//								}
//
//								onApplyJob = View.OnClickListener {
//												job?.run {
//																val jobUrl: String? =
//																				if (externalApplyLink.isNullOrEmpty()) url else externalApplyLink
//																if (jobUrl.isNullOrEmpty()) {
//																				onFailure("Erro")
//																				return@run
//																}
//																try {
//																				JobDetailNavigationImpl.navigateToWebLink(this@JobDetailActivity, jobUrl)
//																} catch (e: Exception) {
//																				onFailure(e.message)
//																}
//												}
//								}
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								job = intent.getParcelableExtraProvider(Constants.PARAMETER_JOB)
//								job?.let {
//												binding.job = job
//												binding.isFavorited = favoritesViewModel.favoritesIds.value?.contains(job?.id) ?: false
//								}
								binding.initButtons()
				}
}