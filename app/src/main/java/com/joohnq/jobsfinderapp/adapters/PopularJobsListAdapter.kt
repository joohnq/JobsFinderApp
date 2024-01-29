package com.joohnq.jobsfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.PopularJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class PopularJobsListAdapter(
    private val popularJobs: List<Job>,
    private val favoriteObserver: (String, PopularJobItemBinding) -> Unit,
    private val onFavourite: (String, PopularJobItemBinding) -> Unit
) : Adapter<PopularJobsListAdapter.PopularJobsViewHolder>() {

    inner class PopularJobsViewHolder(private val binding: PopularJobItemBinding) :
        ViewHolder(binding.root) {
        fun bind(popularJob: Job) {
            favoriteObserver(popularJob.id, binding)
            with(binding) {
                tvPopularJobTitle.text = popularJob.title
                tvPopularJobSallary.text = popularJob.salary
                tvPopularJobLocation.text = popularJob.location
                tvPopularJobCompanyName.text = popularJob.company.name
                Glide
                    .with(tvPopularJobCompanyLogo)
                    .load(popularJob.company.logoUrl)
                    .into(tvPopularJobCompanyLogo)

                btnFavorite.setOnClickListener {
                    favoriteObserver(popularJob.id, binding)
                    onFavourite(popularJob.id, binding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularJobsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PopularJobItemBinding.inflate(layoutInflater, parent, false)
        return PopularJobsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularJobsViewHolder, position: Int) {
        val popularJob = popularJobs[position]
        holder.bind(popularJob)
    }

    override fun getItemCount(): Int = popularJobs.size
}