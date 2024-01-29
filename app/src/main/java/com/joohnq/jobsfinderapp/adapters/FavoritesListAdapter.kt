import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.databinding.SearchJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job

class FavoritesListAdapter(
    private var favoritesJob: List<Job>,
) : Adapter<FavoritesListAdapter.FavoritesJobsViewHolder>() {

    inner class FavoritesJobsViewHolder(private val binding: SearchJobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesJob: Job) {
            with(binding) {
                tvJobTitle.text = favoritesJob.title
                tvJobSalary.text = favoritesJob.salary
                tvJobCompany.text = favoritesJob.company.name
                Glide
                    .with(imgCompanyLogo)
                    .load(favoritesJob.company.logoUrl)
                    .into(imgCompanyLogo)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesJobsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchJobItemBinding.inflate(layoutInflater, parent, false)
        return FavoritesJobsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesJobsViewHolder, position: Int) {
        val popularJob = favoritesJob[position]
        holder.bind(popularJob)
    }

    override fun getItemCount(): Int = favoritesJob.size
}
