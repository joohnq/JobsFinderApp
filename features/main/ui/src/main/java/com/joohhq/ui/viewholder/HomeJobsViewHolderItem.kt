import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.domain.entity.Job
import com.joohnq.ui.databinding.CustomItemJobBinding

class HomeJobsViewHolderItem(
				private val binding: CustomItemJobBinding,
): ViewHolder(binding.root) {
				fun bind(
								job: Job,
								isFavorite: Boolean,
								onClick: (Job) -> Unit,
								onPressFavorite: (String) -> Unit,
				) {
//								binding.job = job
//								binding.onItemClick = View.OnClickListener { onClick(job) }
//								binding.isFavorited = isFavorite
//								binding.onPressFavorite = View.OnClickListener {
//												val newState = !(binding.isFavorited)!!
//												binding.isFavorited = newState
//												onPressFavorite(job.id)
//								}
				}
}