package com.joohnq.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.core.LESEAdapter
import com.joohnq.core.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.home.databinding.CustomItemPopularJobBinding
import com.joohnq.home.viewholders.PopularJobsViewHolderItem
import com.joohnq.job_domain.entities.Job

class PopularJobsListAdapter(
				private val isFavorite: (String) -> Boolean,
				private val onFavourite: (String, Boolean) -> Unit,
				private val onClick: (String) -> Unit,
): LESEAdapter<ViewHolderLoading, ViewHolderEmpty, PopularJobsViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): PopularJobsViewHolderItem {
								val binding = CustomItemPopularJobBinding.inflate(inflater, parent, false)
								return PopularJobsViewHolderItem(binding, isFavorite, onFavourite, onClick)
				}

				override fun bindSuccessViewHolder(holder: PopularJobsViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								holder.bind(item)
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}
