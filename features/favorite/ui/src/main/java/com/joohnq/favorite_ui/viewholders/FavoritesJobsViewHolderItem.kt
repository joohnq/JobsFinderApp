package com.joohnq.favorite_ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.joohnq.core.ui.databinding.CustomItemJobBinding
import com.joohnq.domain.entity.Job

class FavoritesJobsViewHolderItem(private val binding: CustomItemJobBinding):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(
								job: Job,
								isFavorite: Boolean,
								onClick: (Job) -> Unit,
								onPressFavorite: (String) -> Unit,
				) {
								binding.root.setOnClickListener { onClick(job) }
								binding.btnFavorite.setOnClickListener {
//												onPressFavorite(job.id)
								}
				}
}