package com.joohnq.job_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.ShowAllType
import com.joohnq.domain.mappers.ShowAllTypeMapper
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job.ui.databinding.ActivityShowAllBinding
import com.joohnq.job_ui.adapters.ShowAllListAdapter
import com.joohnq.job_ui.navigation.ShowAllNavigationImpl
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.shared_resources.R
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.state.UiState
import com.joohnq.ui.state.UiState.Companion.toRecyclerViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowAllActivity: BaseActivity<ActivityShowAllBinding>() {
				private lateinit var type: ShowAllType
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val showAllListAdapter: ShowAllListAdapter by lazy {
								ShowAllListAdapter(favoritesViewModel) {
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
								RecyclerViewHelper.initVertical(rvShowAll, showAllListAdapter)
				}
}