package com.joohnq.favorite_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.mappers.toRecyclerViewState
import com.joohnq.favorite_ui.adapters.FavoritesListAdapter
import com.joohnq.favorite_ui.databinding.FragmentFavouritesBinding
import com.joohnq.favorite_ui.navigation.FavoriteNavigation
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: Fragment() {
				private var _binding: FragmentFavouritesBinding? = null
				private val binding get() = _binding!!
				private val favoritesViewModel: FavoritesViewModel by activityViewModels()
				private val onFailure = { error: String ->
								SnackBarHelper(binding.root, error)
				}
				private val favoritesListAdapter: FavoritesListAdapter by lazy {
								FavoritesListAdapter(
												onClick = { id: String ->
																FavoriteNavigation.navigateToJobDetailActivity(requireContext(), id)
												},
												onRemove = { jobId: String ->
												},
								)
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.initRv()
				}

				private fun observers() {
								favoritesViewModel.favoritesDetails.distinctUntilChanged()
												.observe(viewLifecycleOwner) { state ->
																favoritesListAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
												}
				}

				private fun FragmentFavouritesBinding.initRv() {
								RecyclerViewHelper.initVerticalWithoutScroll(
												rvFavorites,
												favoritesListAdapter
								)
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?,
								savedInstanceState: Bundle?
				): View {
								_binding = FragmentFavouritesBinding.inflate(
												inflater,
												container,
												false
								)
								observers()
								return binding.root
				}
}