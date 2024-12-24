package com.joohnq.job_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job.ui.databinding.FragmentSearchBinding
import com.joohnq.job_ui.adapters.SearchListAdapter
import com.joohnq.job_ui.navigation.SearchNavigationImpl
import com.joohnq.job_ui.viewmodel.SearchViewModel
import com.joohnq.ui.BaseFragment
import com.joohnq.ui.closeKeyboard
import com.joohnq.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.state.UiState.Companion.toRecyclerViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>() {
				private val searchViewModel: SearchViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val searchJobsListAdapter by lazy {
								SearchListAdapter(favoritesViewModel) {
												SearchNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
				}

				private fun onFailure(error: String?) {
								SnackBarHelper(requireView(), error.toString())
				}

				override fun onStop() {
								super.onStop()
								searchViewModel.resetState()
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.initButtons()
								observers()
								binding.initRv()
//								binding.text = ""
				}

				private fun FragmentSearchBinding.initRv() {
								RecyclerViewHelper.initVerticalWithScrollEvent(rvSearch, searchJobsListAdapter) {
												search()
								}
				}

				private fun FragmentSearchBinding.initButtons() {
								textInputLayoutSearchHome.setEndIconOnClickListener {
//												text = textInputEditTextSearchHome.text.toString()
												searchByButton()
								}
				}

				private fun observers() {
								lifecycleScope.launch {
												searchViewModel.state.collect { state ->
																searchJobsListAdapter.setState(state.jobs.toRecyclerViewState(onFailure = ::onFailure))
												}
								}
				}

				private fun FragmentSearchBinding.search() {
//								if (text == null || text!!.isEmpty()) return

								requireActivity().closeKeyboard()
//								val offset = page * Constants.SEARCH_PER_PAGE
//								val limit = offset + Constants.SEARCH_PER_PAGE
//								searchViewModel.searchJobsReload(text!!, offset.toLong(), limit.toLong())
//								page++
				}

				private fun FragmentSearchBinding.searchByButton() {
//								if (text == null || text!!.isEmpty()) return

								requireActivity().closeKeyboard()
//								searchViewModel.searchJobs(text!!, Constants.SEARCH_PER_PAGE.toLong())
//								page++
				}
}