package com.joohnq.jobsfinderapp.view

import ShowAllListAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityShowAllBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Constants.SHOW_ALL_POPULAR
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class ShowAllActivity : AppCompatActivity() {
    private val tag = "ShowAllActivity"
    private var page = 1
    private lateinit var path: String
    private val binding: ActivityShowAllBinding by lazy {
        ActivityShowAllBinding.inflate(layoutInflater)
    }
    private val jobsViewModel: JobsViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var rvShowAll: RecyclerView
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private val showAllListAdapter: ShowAllListAdapter by lazy {
        ShowAllListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId){drawable ->
                    binding.imgBtnFavorite.setImageResource(drawable)
                }
            },
            onFavourite = { jobId: String ->
                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }

    private fun addFavoritesObserver(
        jobId: String,
        onBinding: (Int) -> Unit
    ) {
        userViewModel.favorites.observe(this) { state ->
            Functions.handleUiState(
                state,
                onSuccess = {
                    this.binding.loadingLayoutShowAll.visibility = View.GONE
                    val isFavorited: Boolean? = userViewModel.isItemFavorite(jobId)
                    val novoDrawable = if (isFavorited == true) {
                        R.drawable.ic_favorites_filled_red_24
                    } else {
                        R.drawable.ic_favorite_24
                    }
                    onBinding(novoDrawable)
                },
                onLoading = {
                    this.binding.loadingLayoutShowAll.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun observers(path: String, lifecycleLifecycleOwner: LifecycleOwner) {
        if (path == SHOW_ALL_POPULAR) {
            jobsViewModel.popularJobs.observe(lifecycleLifecycleOwner) { state ->
                Functions.handleUiState(
                    state,
                    onFailure = { error ->
                        Functions.showErrorWithToast(
                            this@ShowAllActivity,
                            tag,
                            error
                        )
                        binding.loadingLayoutShowAll.visibility = View.VISIBLE
                        jobsViewModel.getPopularJobs(1)
                    },
                    onSuccess = { jobs: List<Job> ->
                        binding.loadingLayoutShowAll.visibility = View.GONE
                        showAllListAdapter.jobs = jobs
                    },
                    onLoading = {
                        if (page == 1) {
                            binding.loadingLayoutShowAll.visibility = View.VISIBLE
                        }
                    }
                )
            }
        } else {
            jobsViewModel.recentPostedJobs.observe(lifecycleLifecycleOwner) { state ->
                binding.tvDontHaveShowAll.visibility = View.GONE

                Functions.handleUiState(
                    state,
                    onFailure = { error ->
                        Functions.showErrorWithToast(
                            this@ShowAllActivity,
                            tag,
                            error
                        )
                        binding.loadingLayoutShowAll.visibility = View.VISIBLE
                        jobsViewModel.getPopularJobs(1)
                    },
                    onSuccess = { jobs: List<Job> ->
                        binding.loadingLayoutShowAll.visibility = View.GONE
                        if (jobs.isEmpty()) {
                            binding.tvDontHaveShowAll.visibility = View.VISIBLE
                            binding.rvShowAll.visibility = View.GONE
                        }
                        showAllListAdapter.jobs = jobs
                    },
                    onLoading = {
                        if (page == 1) {
                            binding.loadingLayoutShowAll.visibility = View.VISIBLE
                        }
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        path = intent.extras?.getString("path").toString()
        initRv()
        observers(path, this)
        binding.imgBtnBack.setOnClickListener {
            finish()
        }
    }

    private fun initRv() {
        rvShowAll = binding.rvShowAll
        rvShowAll.layoutManager = layoutManager
        rvShowAll.adapter = showAllListAdapter

        rvShowAll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 2) {
                    page++
                    if (path == SHOW_ALL_POPULAR) {
                        jobsViewModel.getPopularJobs(page)
                    } else {
                        jobsViewModel.getRecentPostedJobs(page)
                    }
                }
            }
        })
    }
}