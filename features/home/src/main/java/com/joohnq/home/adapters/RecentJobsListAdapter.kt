package com.joohnq.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.core.LESEAdapter
import com.joohnq.core.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.home.databinding.CustomItemRecentPostBinding
import com.joohnq.home.viewholders.RecentJobsViewHolderItem
import com.joohnq.job_domain.entities.Job

class RecentJobsListAdapter(
				private val onClick: (String) -> Unit
): LESEAdapter<ViewHolderLoading, ViewHolderEmpty, RecentJobsViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): RecentJobsViewHolderItem {
								val binding = CustomItemRecentPostBinding.inflate(inflater, parent, false)
								return RecentJobsViewHolderItem(binding, onClick)
				}

				override fun bindSuccessViewHolder(holder: RecentJobsViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								holder.bind(item)
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}