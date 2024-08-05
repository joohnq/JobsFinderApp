package com.joohnq.home.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.home.databinding.CustomItemPopularJobBinding
import com.joohnq.job_domain.entities.Job

class PopularJobsViewHolderItem(
				private val binding: CustomItemPopularJobBinding,
				private val isFavorite: (String) -> Boolean,
				private val onFavorite: (String, Boolean) -> Unit,
				private val onClick: (String) -> Unit
): RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								val id = job.id
								binding.job = job
								binding.isFavorite = isFavorite(id)
								binding.onFavoriteClick = View.OnClickListener {
												val state = !binding.isFavorite!!
												binding.isFavorite = state
												onFavorite(id, state)
								}
								binding.onItemClick = View.OnClickListener {
												onClick(id)
								}
				}
}