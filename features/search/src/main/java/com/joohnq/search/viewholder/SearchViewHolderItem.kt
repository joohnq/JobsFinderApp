package com.joohnq.search.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

class SearchViewHolderItem(
				private val binding: CustomItemJobBinding,
				private val onClick: (String) -> Unit
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								binding.job = job
								binding.onItemClick = View.OnClickListener {
												onClick(job.id)
								}
				}
}