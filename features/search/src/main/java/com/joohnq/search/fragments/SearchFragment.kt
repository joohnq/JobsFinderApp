package com.joohnq.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.joohnq.core.BaseFragment
import com.joohnq.core.closeKeyboard
import com.joohnq.core.constants.Constants
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.search.adapter.SearchListAdapter
import com.joohnq.search.databinding.FragmentSearchBinding
import com.joohnq.search.navigation.SearchNavigationImpl
import com.joohnq.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>() {
				private var page: Int = 1
				private val searchViewModel: SearchViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val onFailure = { error: String? ->
								SnackBarHelper(requireView(), error.toString())
				}
				private val searchJobsListAdapter by lazy {
								SearchListAdapter(favoritesViewModel) {
												SearchNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
				}

				override fun onStop() {
								super.onStop()
								searchViewModel.setJobsNone()
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
								binding.text = ""
				}

				private fun FragmentSearchBinding.initRv() {
								RecyclerViewHelper.initVerticalWithScrollEvent(rvSearch, searchJobsListAdapter) {
												search()
								}
				}

				private fun FragmentSearchBinding.initButtons() {
								textInputLayoutSearchHome.setEndIconOnClickListener {
												text = textInputEditTextSearchHome.text.toString()
												searchByButton()
								}
				}

				private fun observers() {
								searchViewModel.jobsSearch.observe(viewLifecycleOwner) { state ->
												searchJobsListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
								}
				}

				private fun FragmentSearchBinding.search() {
								if (text == null || text!!.isEmpty()) return

								requireActivity().closeKeyboard()
								val offset = page * Constants.SEARCH_PER_PAGE
								val limit = offset + Constants.SEARCH_PER_PAGE
								searchViewModel.searchJobsReload(text!!, offset.toLong(), limit.toLong())
								page++
				}

				private fun FragmentSearchBinding.searchByButton() {
								if (text == null || text!!.isEmpty()) return

								requireActivity().closeKeyboard()
								searchViewModel.searchJobs(text!!, Constants.SEARCH_PER_PAGE.toLong())
								page++
				}
}