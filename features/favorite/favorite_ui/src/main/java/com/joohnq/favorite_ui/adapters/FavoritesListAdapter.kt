package com.joohnq.favorite_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.core.LESEAdapter
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.favorite_ui.viewholders.FavoritesJobsViewHolderItem
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

class FavoritesListAdapter(
				private val onClick: (String) -> Unit,
				private val onRemove: (String) -> Unit
): LESEAdapter<ViewHolderLoading, ViewHolderEmpty, FavoritesJobsViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): FavoritesJobsViewHolderItem {
								val binding = CustomItemJobBinding.inflate(inflater, parent, false)
								return FavoritesJobsViewHolderItem(binding, onClick, onRemove)
				}

				override fun bindSuccessViewHolder(holder: FavoritesJobsViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								holder.bind(item)
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}
