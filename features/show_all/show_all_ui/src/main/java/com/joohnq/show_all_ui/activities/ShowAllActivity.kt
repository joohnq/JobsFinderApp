package com.joohnq.show_all_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.joohnq.core.BaseActivity
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.core.state.UiState
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.shared_resources.R
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.show_all_domain.mappers.ShowAllTypeMapper
import com.joohnq.show_all_ui.adapters.ShowAllListAdapter
import com.joohnq.show_all_ui.databinding.ActivityShowAllBinding
import com.joohnq.show_all_ui.navigation.ShowAllNavigationImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAllActivity: BaseActivity<ActivityShowAllBinding>() {
				private lateinit var type: ShowAllType
				private val jobsViewModel: JobsViewModel by viewModels()
				private val onFailure = { error: String ->
								SnackBarHelper(binding.root, error)
				}
				private val showAllListAdapter: ShowAllListAdapter by lazy {
								ShowAllListAdapter(
												onClick = { id: String ->
																ShowAllNavigationImpl.navigateToJobDetailActivity(this, id)
												},
								)
				}

				private fun ActivityShowAllBinding.observers() {
								when (type) {
												ShowAllType.REMOTE_JOBS -> {
																topAppBar.setTitle(R.string.remote_jobs)
																observeSpecificJobByType(jobsViewModel.remoteJobs)
												}

												ShowAllType.FULL_TIME -> {
																topAppBar.setTitle(R.string.full_time_jobs)
																observeSpecificJobByType(jobsViewModel.fullTimeJobs)
												}

												ShowAllType.PART_TIME -> {
																topAppBar.setTitle(R.string.part_time_jobs)
																observeSpecificJobByType(jobsViewModel.partTimeJobs)
												}
								}
				}

				private fun observeSpecificJobByType(item: LiveData<UiState<List<Job>>>) {
								item.observe(this@ShowAllActivity) { state ->
												showAllListAdapter.setState(
																state.toRecyclerViewState(
																				onFailure = onFailure
																)
												)
								}
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