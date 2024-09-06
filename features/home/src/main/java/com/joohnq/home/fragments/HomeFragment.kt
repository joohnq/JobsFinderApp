package com.joohnq.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.joohnq.core.BaseFragment
import com.joohnq.core.helper.PopUpMenuHelper
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.core.state.UiState
import com.joohnq.core.state.getDataOrNull
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.home.R
import com.joohnq.home.adapters.HomeJobsListAdapter
import com.joohnq.home.databinding.FragmentHomeBinding
import com.joohnq.home.navigation.HomeNavigationImpl
import com.joohnq.home.viewmodel.HomeViewModel
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.getUserOccupationOrNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val userViewModel: UserViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val homeViewModel: HomeViewModel by viewModels()
				private val onFailure = { error: String? ->
								SnackBarHelper(requireView(), error.toString())
				}
				private val homeJobsListAdapter by lazy {
								HomeJobsListAdapter(favoritesViewModel) {
												HomeNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								(activity as? AppCompatActivity)?.setSupportActionBar(binding.topBar)
								binding.initRv()
								binding.bindButtons()
								binding.observers()
				}

				private fun FragmentHomeBinding.bindButtons() {
								homeCardViewRemoteJob.setOnClickListener {
												ShowAllType.REMOTE_JOBS.navigateToShowAllActivityWithShowAllType()
								}
								homeCardViewFullTime.setOnClickListener {
												ShowAllType.FULL_TIME.navigateToShowAllActivityWithShowAllType()
								}
								homeCardViewPartTime.setOnClickListener {
												ShowAllType.PART_TIME.navigateToShowAllActivityWithShowAllType()
								}
								profileImage.setOnClickListener { it.initPopUpMenu() }
								swiperefresh.setOnRefreshListener { homeViewModel.getHomeJobs(userViewModel.user.getUserOccupationOrNull()) }
								setJobsCount(jobsViewModel.remoteJobs) { remoteJobsCount = it }
								setJobsCount(jobsViewModel.partTimeJobs) { partTimeJobsCount = it }
								setJobsCount(jobsViewModel.fullTimeJobs) { fullTimeJobsCount = it }
				}

				private fun ShowAllType.navigateToShowAllActivityWithShowAllType() {
								HomeNavigationImpl.navigateToShowAllActivity(
												requireContext(),
												this
								)
				}

				private fun View.initPopUpMenu() {
								PopUpMenuHelper.invoke(requireContext(), this, R.menu.toolbar_menu) { id ->
												when (id) {
																R.id.profileActivity -> {
																				HomeNavigationImpl.navigateToProfileActivity(requireContext())
																				true
																}
																R.id.logoutButton -> {
																				userViewModel.signOut()
																				true
																}
																else -> false
												}
								}
				}

				private fun setJobsCount(
								state: LiveData<UiState<List<Job>>>,
								onUpdate: (String) -> Unit
				) {
								state.observe(viewLifecycleOwner) {
												onUpdate(
																when (it) {
																				is UiState.Loading -> getString(com.joohnq.shared_resources.R.string.loading_dots)
																				is UiState.Success -> it.data.size.toString()
																				else -> "0"
																}
												)
								}
				}

				private fun FragmentHomeBinding.initRv() {
								RecyclerViewHelper.initVertical(rvHomeJobs, homeJobsListAdapter)
				}

				private fun FragmentHomeBinding.observers() {
								homeViewModel.homeJobs.observe(viewLifecycleOwner) { state ->
												homeJobsListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
												binding.swiperefresh.isRefreshing = false
								}
								userViewModel.user.observe(viewLifecycleOwner) { state -> user = state }
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentHomeBinding =
								FragmentHomeBinding.inflate(
												inflater,
												container,
												false
								)
}