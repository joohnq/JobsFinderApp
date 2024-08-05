package com.joohnq.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.home.adapters.PopularJobsListAdapter
import com.joohnq.home.adapters.RecentJobsListAdapter
import com.joohnq.home.databinding.FragmentHomeBinding
import com.joohnq.home.navigation.HomeNavigation
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
				private var _binding: FragmentHomeBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by activityViewModels()
				private val favoritesViewModel: FavoritesViewModel by activityViewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val onFailure = { error: String? -> SnackBarHelper(requireView(), error.toString()) }
				private val popularJobsListAdapter by lazy {
								PopularJobsListAdapter(
												isFavorite = { _ -> true },
												onFavourite = { _, _ -> },
												onClick = { id: String ->
																HomeNavigation.navigateToJobDetailActivity(requireContext(), id)
												},
								)
				}
				private val recentJobsListAdapter by lazy {
								RecentJobsListAdapter { id: String ->
												HomeNavigation.navigateToJobDetailActivity(requireContext(), id)
								}
				}

				override fun onDestroyView() {
								super.onDestroyView()
								_binding = null
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.viewmodel = userViewModel
								binding.initRvs()
								binding.bindButtons()
								observers()
				}

				private fun FragmentHomeBinding.bindButtons() {
								textInputEditTextSearchHome.setOnClickListener {
												HomeNavigation.navigateToSearchActivity(requireContext())
								}
								tvShowAllPopular.setOnClickListener {
												HomeNavigation.navigateToShowAllActivity(
																requireContext(),
																ShowAllType.POPULAR.toString()
												)
								}
								tvRecentPostShowAll.setOnClickListener {
												HomeNavigation.navigateToShowAllActivity(
																requireContext(),
																ShowAllType.RECENT_POST.toString()
												)
								}
				}

				private fun FragmentHomeBinding.initRvs() {
								RecyclerViewHelper.initHorizontal(rvPopularPost, popularJobsListAdapter)
								RecyclerViewHelper.initVertical(rvRecentPost, recentJobsListAdapter)
				}

				private fun observers() {
								jobsViewModel.popularJobs.observe(viewLifecycleOwner) { state ->
												popularJobsListAdapter.setState(state.toRecyclerViewState(8, onFailure = onFailure))
								}
								jobsViewModel.recentPostedJobs.observe(viewLifecycleOwner) { state ->
												recentJobsListAdapter.setState(state.toRecyclerViewState(8, onFailure = onFailure))
								}
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