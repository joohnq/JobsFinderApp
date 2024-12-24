package com.joohnq.favorite.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.joohnq.favorite.ui.databinding.FragmentFavouritesBinding
import com.joohnq.favorite.ui.adapters.FavoritesListAdapterJob
import com.joohnq.favorite.ui.navigation.FavoriteNavigationImpl
import com.joohnq.favorite.ui.viewmodel.FavoritesViewModel
import com.joohnq.ui.BaseFragment
import com.joohnq.core.ui.helper.RecyclerViewHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.core.domain.entity.UiState.Companion.toRecyclerViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavouritesBinding>() {
				private val favoritesViewModel: FavoritesViewModel by activityViewModels()
				private val favoritesListAdapter: FavoritesListAdapterJob by lazy {
								FavoritesListAdapterJob(favoritesViewModel) {
												FavoriteNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
				}

				private fun onFailure(error: String) {
								SnackBarHelper(binding.root, error)
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.initRv()
								binding.observers()
				}

				private fun FragmentFavouritesBinding.observers() {
								favoritesViewModel.favoritesDetails.observe(viewLifecycleOwner) { state ->
												favoritesListAdapter.setState(state.toRecyclerViewState(onFailure = ::onFailure))
								}
				}

				private fun FragmentFavouritesBinding.initRv() {
								com.joohnq.core.ui.helper.RecyclerViewHelper.initVerticalWithoutScroll(
												rvFavorites,
												favoritesListAdapter
								)
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentFavouritesBinding = FragmentFavouritesBinding.inflate(
								inflater,
								container,
								false
				)
}