import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.databinding.PopularJobItemBinding
import com.joohnq.jobsfinderapp.databinding.SearchJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class SearchListAdapter(
    private val favoriteObserver: (String, SearchJobItemBinding) -> Unit,
    private val onFavourite: (String) -> Unit
) : Adapter<SearchListAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(private val binding: SearchJobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            favoriteObserver(job.id, binding)
            with(binding) {
                tvJobTitle.text = job.title
                with(job.salary) {
                    val salary = "$symbol$entry - $end/$time"
                    tvJobSalary.text = salary
                }
                tvJobCompany.text = job.company.name
                Glide
                    .with(imgCompanyLogo)
                    .load(job.company.logoUrl)
                    .into(imgCompanyLogo)

                imgBtnFavorite.setOnClickListener {
                    favoriteObserver(job.id, binding)
                    onFavourite(job.id)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchJobItemBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobs.size
}
