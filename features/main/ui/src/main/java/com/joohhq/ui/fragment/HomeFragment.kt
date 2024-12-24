package com.joohnq.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.domain.entity.ShowAllType
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.main.navigation.HomeNavigationImpl
import com.joohnq.main.viewmodel.HomeViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.main.R
import com.joohhq.ui.adapter.HomeJobsListAdapter
import com.joohnq.main.databinding.FragmentHomeBinding
import com.joohnq.ui.BaseFragment
import com.joohnq.ui.helper.PopUpMenuHelper
import com.joohnq.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.state.UiState.Companion.toRecyclerViewState
import com.joohnq.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val userViewModel: UserViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val homeViewModel: HomeViewModel by viewModels()
				private val homeJobsListAdapter by lazy {
								HomeJobsListAdapter(favoritesViewModel) {
												HomeNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
				}

				private fun onFailure(error: String?) {
								SnackBarHelper(requireView(), error.toString())
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
												ShowAllType.REMOTE.navigateToShowAllActivityWithShowAllType()
								}
								homeCardViewFullTime.setOnClickListener {
												ShowAllType.FULL_TIME.navigateToShowAllActivityWithShowAllType()
								}
								homeCardViewPartTime.setOnClickListener {
												ShowAllType.PART_TIME.navigateToShowAllActivityWithShowAllType()
								}
//								profileImage.setOnClickListener { it.initPopUpMenu() }
//								swiperefresh.setOnRefreshListener { homeViewModel.getHomeJobs(occupation) }
//								setJobsCount(jobsViewModel.remoteJobs) { remoteJobsCount = it }
//								setJobsCount(jobsViewModel.partTimeJobs) { partTimeJobsCount = it }
//								setJobsCount(jobsViewModel.fullTimeJobs) { fullTimeJobsCount = it }
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
//																				userViewModel.signOut()
																				true
																}

																else -> false
												}
								}
				}

				private fun FragmentHomeBinding.initRv() {
								RecyclerViewHelper.initVertical(rvHomeJobs, homeJobsListAdapter)
				}

				private fun FragmentHomeBinding.observers() {
								lifecycleScope.launch {
												homeViewModel.state.collect { state ->
																homeJobsListAdapter.setState(state.jobs.toRecyclerViewState(onFailure = ::onFailure))
												}
								}
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