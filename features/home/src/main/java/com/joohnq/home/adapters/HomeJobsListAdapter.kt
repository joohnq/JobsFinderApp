package com.joohnq.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.core.adapter.LESEAdapter
import com.joohnq.core.databinding.CustomItemJobBinding
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.core.viewholder.ViewHolderNothing
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.home.viewholders.HomeJobsViewHolderItem
import com.joohnq.job_domain.entities.Job

class HomeJobsListAdapter(
				private val favoritesViewModel: FavoritesViewModel,
				private val onClick: (Job) -> Unit
): LESEAdapter<ViewHolderNothing, ViewHolderLoading, ViewHolderEmpty, HomeJobsViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): HomeJobsViewHolderItem {
								val binding = CustomItemJobBinding.inflate(inflater, parent, false)
								return HomeJobsViewHolderItem(binding)
				}

				override fun bindSuccessViewHolder(holder: HomeJobsViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								val isFavorite = favoritesViewModel.favoritesIds.value?.contains(item.id) ?: false
								holder.bind(item, isFavorite, onClick) { favoritesViewModel.toggle(it) }
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}