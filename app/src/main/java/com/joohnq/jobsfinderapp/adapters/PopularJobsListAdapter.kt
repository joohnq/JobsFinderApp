package com.joohnq.jobsfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.databinding.PopularJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class PopularJobsListAdapter(
    private val favoriteObserver: (String, PopularJobItemBinding) -> Unit,
    private val onFavourite: (String) -> Unit,
    private val onClick: (Job) -> Unit
) : Adapter<PopularJobsListAdapter.PopularJobsViewHolder>() {

    inner class PopularJobsViewHolder(private val binding: PopularJobItemBinding) :
        ViewHolder(binding.root) {
        fun bind(job: Job) {
            favoriteObserver(job.id, binding)
            with(binding) {
                tvPopularJobTitle.text = job.title
                with(job.salary){
                    val salary = "$symbol$entry - $end/$time"
                    tvPopularJobSallary.text = salary
                }
                tvPopularJobLocation.text = job.location
                tvPopularJobCompanyName.text = job.company.name
                Glide
                    .with(tvPopularJobCompanyLogo)
                    .load(job.company.logoUrl)
                    .into(tvPopularJobCompanyLogo)

                btnFavorite.setOnClickListener {
                    onFavourite(job.id)
                    favoriteObserver(job.id, binding)
                }
                root.setOnClickListener {
                    onClick(job)
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var jobs: List<Job>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularJobsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PopularJobItemBinding.inflate(layoutInflater, parent, false)
        return PopularJobsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularJobsViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobs.size
}