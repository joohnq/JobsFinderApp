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
import com.joohnq.jobsfinderapp.util.Toast
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.view.fragments.JobDetailFragment
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class ShowAllActivity : AppCompatActivity() {
    private var page = 1
    private lateinit var path: String
    private var _binding: ActivityShowAllBinding? = null
    private val binding get() = _binding!!
    private val jobsViewModel: JobsViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var rvShowAll: RecyclerView
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private val showAllListAdapter: ShowAllListAdapter by lazy {
        ShowAllListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId) { drawable ->
                    binding.imgBtnFavorite.setImageResource(drawable)
                }
            },
            onClick = { job ->
                showBottomSheetDialog(job)
            },
            onFavourite = { jobId: String ->
//                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showBottomSheetDialog(job: Job) {
        val dialog = JobDetailFragment(job)
        dialog.show(supportFragmentManager, "JobDetailFragment")
    }

    private fun addFavoritesObserver(
        jobId: String,
        onBinding: (Int) -> Unit
    ) {
        userViewModel.favorites.observe(this) { state ->
            state.handleUiState(
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
                state.handleUiState(
                    onFailure = { error ->
                        Toast(this).invoke(error.toString())
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
                state.handleUiState(
                    onFailure = { error ->
                        Toast(this).invoke(error.toString())
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
        _binding = ActivityShowAllBinding.inflate(layoutInflater)
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