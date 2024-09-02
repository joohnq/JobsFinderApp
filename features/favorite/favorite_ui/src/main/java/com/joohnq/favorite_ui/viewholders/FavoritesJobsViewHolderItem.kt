package com.joohnq.favorite_ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

class FavoritesJobsViewHolderItem(
				private val binding: CustomItemJobBinding,
				private val onClick: (String) -> Unit,
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								val id = job.id
								binding.job = job
								binding.onItemClick = View.OnClickListener {
												onClick(id)
								}
				}
}