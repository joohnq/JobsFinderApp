package com.joohnq.jobsfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ApplicationJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class ApplicationsListAdapter :
    RecyclerView.Adapter<ApplicationsListAdapter.ApplicationsViewHolder>() {
    inner class ApplicationsViewHolder(private val binding: ApplicationJobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            with(binding) {
                tvJobTitle.text = job.title
                tvJobLocation.text = job.location
                with(job.salary) {
                    val salary = "$symbol$entry - $end/$time"
                    tvJobSalary.text = salary
                }
                tvJobCompanyName.text = job.company.name
                tvStatus.text = job.status
                val tv = when (job.status) {
                    "pending" -> {
                        R.color.pendingTv
                    }
                    "finished" -> {
                        R.color.deliveredTv
                    }
                    else -> {
                        R.color.analyzingTv
                    }
                }

                tvStatus.setTextColor(binding.root.context.getColor(tv))
                Glide
                    .with(imgCompanyLogo)
                    .load(job.company.logoUrl)
                    .into(imgCompanyLogo)
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
    var applications: MutableList<Job>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ApplicationJobItemBinding.inflate(layoutInflater, parent, false)
        return ApplicationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplicationsViewHolder, position: Int) {
        val job = applications[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = applications.size

}