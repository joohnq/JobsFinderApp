package com.joohnq.favorite_ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.favorite_ui.databinding.CustomItemFavoriteBinding
import com.joohnq.job_domain.entities.Job

class FavoritesJobsViewHolderItem(
				private val binding: CustomItemFavoriteBinding,
				private val onClick: (String) -> Unit,
				private val onRemove: (String) -> Unit
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								val id = job.id
								binding.job = job
								binding.isFavorite = true
								binding.onFavoriteClick = View.OnClickListener {
												binding.isFavorite = false
												onRemove(id)
								}

								binding.onItemClick = View.OnClickListener {
												onClick(id)
								}
				}
}