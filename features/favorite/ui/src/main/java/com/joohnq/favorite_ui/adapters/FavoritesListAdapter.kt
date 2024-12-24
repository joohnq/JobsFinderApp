package com.joohnq.favorite_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.ui.adapter.LESEAdapter
import com.joohnq.core.databinding.CustomItemJobBinding
import com.joohnq.ui.state.RecyclerViewState
import com.joohnq.ui.viewholder.ViewHolderEmpty
import com.joohnq.ui.viewholder.ViewHolderError
import com.joohnq.ui.viewholder.ViewHolderLoading
import com.joohnq.ui.viewholder.ViewHolderNothing
import com.joohnq.favorite_ui.viewholders.FavoritesJobsViewHolderItem
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.domain.entity.Job

class FavoritesListAdapter(
				private val favoritesViewModel: FavoritesViewModel,
				private val onClick: (Job) -> Unit,
): com.joohnq.ui.adapter.LESEAdapter<com.joohnq.ui.viewholder.ViewHolderNothing, com.joohnq.ui.viewholder.ViewHolderLoading, com.joohnq.ui.viewholder.ViewHolderEmpty, FavoritesJobsViewHolderItem, com.joohnq.ui.viewholder.ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): FavoritesJobsViewHolderItem {
								val binding = CustomItemJobBinding.inflate(inflater, parent, false)
								return FavoritesJobsViewHolderItem(binding)
				}

				override fun bindSuccessViewHolder(holder: FavoritesJobsViewHolderItem, position: Int) {
								val item = (uiState as com.joohnq.ui.state.RecyclerViewState.Success<Job>).data[position]
								val isFavorite = favoritesViewModel.favoritesIds.value?.contains(item.id) ?: false
								holder.bind(item, isFavorite, onClick) { favoritesViewModel.toggle(it) }
				}

				override fun bindErrorViewHolder(holder: com.joohnq.ui.viewholder.ViewHolderError) {
								val errorMessage = (uiState as com.joohnq.ui.state.RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}
