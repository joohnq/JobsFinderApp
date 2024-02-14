package com.joohnq.jobsfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.databinding.RecentPostItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class RecentJobsListAdapter(
    private val onClick: (Job) -> Unit
) : Adapter<RecentJobsListAdapter.RecentJobsListViewHolder>() {

    inner class RecentJobsListViewHolder(private val binding: RecentPostItemBinding) :
        ViewHolder(binding.root) {
        fun bind(job: Job) {
            with(binding) {
                tvJobTitle.text = job.title
                with(job.salary) {
                    val salary = "$symbol$entry - $end/$time"
                    tvJobSalary.text = salary
                }
                tvJobType.text = job.type
                Glide
                    .with(imgCompanyLogo)
                    .load(job.company.logoUrl)
                    .into(imgCompanyLogo)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentJobsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecentPostItemBinding.inflate(layoutInflater, parent, false)
        return RecentJobsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentJobsListViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobs.size
}