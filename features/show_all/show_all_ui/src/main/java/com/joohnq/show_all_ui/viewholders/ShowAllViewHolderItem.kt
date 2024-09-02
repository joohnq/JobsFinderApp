package com.joohnq.show_all_ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

class ShowAllViewHolderItem(
				private val binding: CustomItemJobBinding,
				private val onClick: (String) -> Unit
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								val id = job.id
								binding.job = job
								with(binding) {
												root.setOnClickListener { onClick(id) }

//												imgBtnFavorite.setOnClickListener {
//																onFavourite(job.id)
//																binding.isFavorite = isFavorite(id)
//												}
								}
				}
}