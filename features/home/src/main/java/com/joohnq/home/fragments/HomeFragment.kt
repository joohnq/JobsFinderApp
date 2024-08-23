package com.joohnq.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.joohnq.core.helper.PopUpMenuHelper
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.home.R
import com.joohnq.home.adapters.HomeJobsListAdapter
import com.joohnq.home.databinding.FragmentHomeBinding
import com.joohnq.home.navigation.HomeNavigationImpl
import com.joohnq.home.viewmodel.HomeViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
				private var _binding: FragmentHomeBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val homeViewModel: HomeViewModel by viewModels()
				private val onFailure = { error: String? -> SnackBarHelper(requireView(), error.toString()) }
				private val homeJobsListAdapter by lazy {
								HomeJobsListAdapter(
												onClick = { id: String ->
																HomeNavigationImpl.navigateToJobDetailActivity(requireContext(), id)
												},
								)
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								(activity as? AppCompatActivity)?.setSupportActionBar(binding.topBar)
								binding.initRvs()
								binding.bindButtons()
								binding.observers()
				}

				private fun FragmentHomeBinding.bindButtons() {
								homeCardViewRemoteJob.setOnClickListener {
												HomeNavigationImpl.navigateToShowAllActivity(requireContext(), ShowAllType.REMOTE_JOBS)
								}
								homeCardViewFullTime.setOnClickListener {
												HomeNavigationImpl.navigateToShowAllActivity(requireContext(), ShowAllType.FULL_TIME)
								}
								homeCardViewPartTime.setOnClickListener {
												HomeNavigationImpl.navigateToShowAllActivity(requireContext(), ShowAllType.PART_TIME)
								}
								profileImage.setOnClickListener {
												PopUpMenuHelper.invoke(requireContext(), it, R.menu.toolbar_menu) {
																when (it) {
																				R.id.profileActivity -> {
																								HomeNavigationImpl.navigateToProfileActivity(requireContext())
																								true
																				}

																				else -> false
																}
												}
								}
				}

				private fun FragmentHomeBinding.initRvs() {
								RecyclerViewHelper.initVertical(rvHomeJobs, homeJobsListAdapter)
				}

				private fun FragmentHomeBinding.observers() {
								homeViewModel.homeJobs.observe(viewLifecycleOwner) { state ->
												homeJobsListAdapter.setState(state.toRecyclerViewState(8, onFailure = onFailure))
								}
								userViewModel.user.observe(viewLifecycleOwner) { state -> user = state }
								jobsViewModel.remoteJobs.observe(viewLifecycleOwner) { state -> remoteJobs = state }
								jobsViewModel.fullTimeJobs.observe(viewLifecycleOwner) { state -> fullTimeJobs = state }
								jobsViewModel.partTimeJobs.observe(viewLifecycleOwner) { state -> partTimeJobs = state }
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?,
								savedInstanceState: Bundle?
				): View {
								_binding = FragmentHomeBinding.inflate(
												inflater,
												container,
												false
								)
								return binding.root
				}
}