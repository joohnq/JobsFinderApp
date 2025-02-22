package com.joohnq.job.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.job.domain.entity.Job
import com.joohnq.job.domain.entity.ShowAllType
import com.joohnq.core.domain.entity.UiState
import com.joohnq.core.domain.entity.UiState.Companion.toRecyclerViewState
import com.joohnq.job.domain.mappers.ShowAllTypeMapper
import com.joohnq.favorite.ui.viewmodel.FavoritesViewModel
import com.joohnq.job.ui.databinding.ActivityShowAllBinding
import com.joohnq.shared_resources.R
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.adapter.ShowAllListAdapterJob
import com.joohnq.core.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.navigation.ShowAllNavigationImpl
import com.joohnq.ui.viewmodel.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowAllActivity: BaseActivity<ActivityShowAllBinding>() {
				private lateinit var type: ShowAllType
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val showAllListAdapter: ShowAllListAdapterJob by lazy {
								ShowAllListAdapterJob(favoritesViewModel) {
												ShowAllNavigationImpl.navigateToJobDetailActivity(this@ShowAllActivity, it)
								}
				}

				private fun onFailure(error: String) {
								SnackBarHelper(binding.root, error)
				}

				private fun ActivityShowAllBinding.observers() {
								lifecycleScope.launch {
												jobsViewModel.state.collect { state ->
																val title = when (type) {
																				ShowAllType.REMOTE -> R.string.remote_jobs
																				ShowAllType.FULL_TIME -> R.string.full_time_jobs
																				ShowAllType.PART_TIME -> R.string.part_time_jobs
																}
																topAppBar.setTitle(title)
																state.remote.observeSpecificJobByType()
												}
								}
				}

				private fun UiState<List<Job>>.observeSpecificJobByType() {
								showAllListAdapter.setState(
												toRecyclerViewState(
																onFailure = ::onFailure
												)
								)
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityShowAllBinding = ActivityShowAllBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								type = ShowAllTypeMapper.toShowAllType(
												intent.extras?.getString("type").toString()
								)
								binding.initRv()
								binding.bindButtons()
								binding.observers()
				}

				private fun ActivityShowAllBinding.bindButtons() {
								topAppBar.setNavigationOnClickListener { finish() }
				}

				private fun ActivityShowAllBinding.initRv() {
								com.joohnq.core.ui.helper.RecyclerViewHelper.initVertical(rvShowAll, showAllListAdapter)
				}
}