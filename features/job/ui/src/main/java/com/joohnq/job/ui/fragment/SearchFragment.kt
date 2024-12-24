package com.joohnq.job.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.core.domain.entity.UiState.Companion.toRecyclerViewState
import com.joohnq.favorite.ui.viewmodel.FavoritesViewModel
import com.joohnq.job.ui.databinding.FragmentSearchBinding
import com.joohnq.ui.BaseFragment
import com.joohnq.ui.adapter.SearchListAdapterJob
import com.joohnq.ui.closeKeyboard
import com.joohnq.core.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.job.ui.navigation.SearchNavigationImpl
import com.joohnq.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>() {
				private val searchViewModel: SearchViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val searchJobsListAdapter by lazy {
								SearchListAdapterJob(favoritesViewModel) {
												com.joohnq.job.ui.navigation.SearchNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
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
								com.joohnq.core.ui.helper.RecyclerViewHelper.initVerticalWithScrollEvent(rvSearch, searchJobsListAdapter) {
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