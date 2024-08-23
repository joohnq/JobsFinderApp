package com.joohnq.application_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.application_ui.databinding.CustomItemApplicationBinding
import com.joohnq.application_ui.viewholder.ApplicationsViewHolderItem
import com.joohnq.core.LESEAdapter
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.job_domain.entities.Job

class ApplicationsListAdapter:
				LESEAdapter<ViewHolderLoading, ViewHolderEmpty, ApplicationsViewHolderItem, ViewHolderError>() {
				override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): ApplicationsViewHolderItem {
								val binding = CustomItemApplicationBinding.inflate(inflater, parent, false)
								return ApplicationsViewHolderItem(binding)
				}

				override fun bindSuccessViewHolder(holder: ApplicationsViewHolderItem, position: Int) {
								val item = (uiState as RecyclerViewState.Success<Job>).data[position]
								holder.bind(item)
				}

				override fun bindErrorViewHolder(holder: ViewHolderError) {
								val errorMessage = (uiState as RecyclerViewState.Error).error
								holder.bind(errorMessage)
				}
}