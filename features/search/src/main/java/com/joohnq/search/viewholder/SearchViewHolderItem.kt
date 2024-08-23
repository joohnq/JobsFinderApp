package com.joohnq.search.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

class SearchViewHolderItem(
				private val binding: CustomItemJobBinding,
				private val favoriteObserver: (String) -> Boolean,
				private val onFavorite: (String, Boolean) -> Unit,
				private val onClick: (String) -> Unit
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								binding.isFavorite = favoriteObserver(job.id)
								binding.job = job
								binding.onItemClick = View.OnClickListener {
												onClick(job.id)
								}
								binding.onFavoriteClick = View.OnClickListener {
												onFavorite(job.id, binding.isFavorite!!)
												binding.isFavorite = favoriteObserver(job.id)
								}
				}
}