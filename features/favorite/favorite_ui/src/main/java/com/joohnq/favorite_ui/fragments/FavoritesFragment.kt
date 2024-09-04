package com.joohnq.favorite_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.joohnq.core.BaseFragment
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.favorite_ui.adapters.FavoritesListAdapter
import com.joohnq.favorite_ui.databinding.FragmentFavouritesBinding
import com.joohnq.favorite_ui.navigation.FavoriteNavigationImpl
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavouritesBinding>() {
				private val favoritesViewModel: FavoritesViewModel by activityViewModels()
				private val onFailure = { error: String ->
								SnackBarHelper(binding.root, error)
				}
				private val favoritesListAdapter: FavoritesListAdapter by lazy {
								FavoritesListAdapter(favoritesViewModel) {
												FavoriteNavigationImpl.navigateToJobDetailActivity(requireContext(), it)
								}
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
												favoritesListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
								}
				}

				private fun FragmentFavouritesBinding.initRv() {
								RecyclerViewHelper.initVerticalWithoutScroll(
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