package com.joohnq.home.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.home.databinding.CustomItemRecentPostBinding
import com.joohnq.job_domain.entities.Job

class RecentJobsViewHolderItem(
				private val binding: CustomItemRecentPostBinding,
				private val onClick: (String) -> Unit
): ViewHolder(binding.root) {
				fun bind(job: Job) {
								binding.job = job
								binding.onItemClick = View.OnClickListener {
												onClick(job.id)
								}
				}
}