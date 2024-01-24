package com.joohnq.jobsfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.databinding.RecentPostItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class RecentJobsListAdapter(
    private val recentPostedJobs: List<Job>
) : Adapter<RecentJobsListAdapter.RecentJobsListViewHolder>() {

    inner class RecentJobsListViewHolder(private val binding: RecentPostItemBinding) :
        ViewHolder(binding.root) {
        fun bind(recentPostedJob: Job) {
            with(binding) {
                tvJobTitle.text = recentPostedJob.title
                tvJobSalary.text = recentPostedJob.salary
                tvJobType.text = recentPostedJob.type
                Glide
                    .with(imgCompanyLogo)
                    .load(recentPostedJob.company.logoUrl)
                    .into(imgCompanyLogo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentJobsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecentPostItemBinding.inflate(layoutInflater, parent, false)
        return RecentJobsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentJobsListViewHolder, position: Int) {
        val recentPostJob = recentPostedJobs[position]
        holder.bind(recentPostJob)
    }

    override fun getItemCount(): Int = recentPostedJobs.size
}