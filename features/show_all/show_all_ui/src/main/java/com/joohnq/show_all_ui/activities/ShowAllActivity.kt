package com.joohnq.show_all_ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.core.state.UiState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.show_all_domain.mappers.ShowAllTypeMapper
import com.joohnq.show_all_ui.adapters.ShowAllListAdapter
import com.joohnq.show_all_ui.databinding.ActivityShowAllBinding
import com.joohnq.show_all_ui.navigation.ShowAllNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAllActivity: AppCompatActivity() {
				private var page = 1
				private lateinit var path: ShowAllType
				private var _binding: ActivityShowAllBinding? = null
				private val binding get() = _binding!!
				private val jobsViewModel: JobsViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val onFailure = { error: String ->
								SnackBarHelper(binding.root, error)
								jobsViewModel.getPopularJobs(1)
								binding.loadingLayoutShowAll.visibility = View.VISIBLE
				}
				private val showAllListAdapter: ShowAllListAdapter by lazy {
								ShowAllListAdapter(
												isFavorite = { _ -> true },
												onClick = { id: String ->
																ShowAllNavigation.navigateToJobDetailActivity(this, id)
												},
												onFavourite = { jobId: String ->
//                userViewModel.handleJobIdFavorite(jobId)
												},
								)
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				private fun observers() {
								when (path) {
												ShowAllType.POPULAR -> {
																jobsViewModel.popularJobs.observe(this@ShowAllActivity) { state ->
																				showAllListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
																}
												}

												ShowAllType.RECENT_POST -> {
																jobsViewModel.recentPostedJobs.observe(this@ShowAllActivity) { state ->
																				showAllListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
																}
												}
								}
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								_binding = ActivityShowAllBinding.inflate(layoutInflater)
								setContentView(binding.root)
								path = ShowAllTypeMapper.toShowAllType(
												intent.extras?.getString("type").toString()
								)
								binding.initRv()
								binding.bindButtons()
								observers()
				}

				private fun ActivityShowAllBinding.bindButtons() {
								imgBtnBack.setOnClickListener { finish() }
				}

				private fun ActivityShowAllBinding.initRv() {
								RecyclerViewHelper.initVerticalWithScrollEvent(rvShowAll, showAllListAdapter) {
												page++
												when (path) {
																ShowAllType.POPULAR -> jobsViewModel.getPopularJobs(
																				page
																)

																ShowAllType.RECENT_POST -> jobsViewModel.getRecentPostedJobs(
																				page
																)
												}
								}
				}
}