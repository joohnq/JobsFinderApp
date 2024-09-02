package com.joohnq.show_all_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.core.LESEAdapter
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding
import com.joohnq.show_all_ui.viewholders.ShowAllViewHolderItem

class ShowAllListAdapter(
				private val onClick: (String) -> Unit,
): LESEAdapter<ViewHolderLoading, ViewHolderEmpty, ShowAllViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): ShowAllViewHolderItem {
								val binding = CustomItemJobBinding.inflate(inflater, parent, false)
								return ShowAllViewHolderItem(binding,onClick)
				}

				override fun bindSuccessViewHolder(holder: ShowAllViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								holder.bind(item)
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}
