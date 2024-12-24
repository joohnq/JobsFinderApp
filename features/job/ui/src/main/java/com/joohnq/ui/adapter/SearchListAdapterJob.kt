package com.joohnq.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.RecyclerViewState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_ui.viewholders.SearchViewHolderItem
import com.joohnq.ui.databinding.CustomItemJobBinding
import com.joohnq.ui.viewholder.ViewHolderEmpty
import com.joohnq.ui.viewholder.ViewHolderError
import com.joohnq.ui.viewholder.ViewHolderLoading
import com.joohnq.ui.viewholder.ViewHolderNothing

class SearchListAdapterJob(
				private val favoritesViewModel: FavoritesViewModel,
				private val onClick: (Job) -> Unit
): JobLESEAdapter<ViewHolderNothing, ViewHolderLoading, ViewHolderEmpty, SearchViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): SearchViewHolderItem {
								val binding = CustomItemJobBinding.inflate(inflater, parent, false)
								return SearchViewHolderItem(binding)
				}

				override fun bindSuccessViewHolder(holder: SearchViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								val isFavorite = favoritesViewModel.favoritesIds.value?.contains(item.id) ?: false
								holder.bind(item, isFavorite, onClick) { favoritesViewModel.toggle(it) }
				}

				override fun bindErrorViewHolder(holder: com.joohnq.ui.viewholder.ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}