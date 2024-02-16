package com.joohnq.jobsfinderapp.view.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.adapters.PopularJobsListAdapter
import com.joohnq.jobsfinderapp.adapters.RecentJobsListAdapter
import com.joohnq.jobsfinderapp.databinding.FragmentHomeBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Constants.SHOW_ALL_POPULAR
import com.joohnq.jobsfinderapp.util.Constants.SHOW_ALL_RECENT_POST
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.SearchActivity
import com.joohnq.jobsfinderapp.view.ShowAllActivity
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.FiltersViewModel
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val tag = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val filtersViewModel: FiltersViewModel by activityViewModels()
    private val jobsViewModel: JobsViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var rvPopularPost: RecyclerView
    private lateinit var rvRecentPost: RecyclerView
    private val popularJobsListAdapter by lazy {
        PopularJobsListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId) { drawable ->
                    binding.btnFavorite.setImageResource(drawable)
                }
            },
            onFavourite = { jobId: String ->
                userViewModel.handleJobIdFavorite(jobId)
            },
            onClick = { job ->
                showBottomSheetDialog(job)
            }
        )
    }
    private val recentJobsListAdapter by lazy {
        RecentJobsListAdapter { job ->
            showBottomSheetDialog(job)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRvs()
        observer()
        bindButtons()
    }

    private fun bindButtons() {
        with(binding) {
            textInputEditTextSearchHome.setOnClickListener {
                initSearchActivity()
            }

            textInputEditTextSearchHome.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    val intent = Intent(requireContext(), SearchActivity::class.java)
                    val options = ActivityOptions
                        .makeSceneTransitionAnimation(requireActivity())
                    startActivity(intent, options.toBundle())
                }
            }

            tvShowAllPopular.setOnClickListener {
                initShowAll(SHOW_ALL_POPULAR)
            }

            tvRecentPostShowAll.setOnClickListener {
                initShowAll(SHOW_ALL_RECENT_POST)
            }
        }
    }

    private fun initSearchActivity(runOnLoad: Boolean? = null) {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(requireActivity())
        intent.putExtra("runOnLoad", runOnLoad)
        startActivity(intent, options.toBundle())
    }

    private fun initShowAll(path: String) {
        val intent = Intent(requireContext(), ShowAllActivity::class.java)
        intent.putExtra("path", path)
        startActivity(intent)
    }

    private fun showBottomSheetDialog(job: Job) {
        val dialog = JobDetailFragment(job)
        dialog.show(requireActivity().supportFragmentManager, "JobDetailFragment")
    }

    private fun initRvs() {
        rvPopularPost = binding.rvPopularPost
        rvRecentPost = binding.rvRecentPost
        rvPopularPost.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecentPost.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvPopularPost.adapter = popularJobsListAdapter
        rvRecentPost.adapter = recentJobsListAdapter
    }

    private fun observer() {
        userViewModel.user.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    Functions.showErrorWithToast(
                        requireContext(),
                        tag,
                        error
                    )
                },
                onSuccess = { data ->
                    val userPhoto = data?.imageUrl
                    val profileImage = binding.includeToolbar.profileImage
                    val userName = data?.name?.let { Functions.getOneWord(it) }
                    binding.includeToolbar.userName.text = userName
                    if (!userPhoto.isNullOrEmpty()) {
                        Glide
                            .with(requireContext())
                            .load(userPhoto)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_generic)
                            .into(profileImage)
                    }
                },
            )
        }
        jobsViewModel.popularJobs.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    Functions.showErrorWithToast(
                        requireContext(),
                        tag,
                        error
                    )
                    binding.pbPopularJobs.visibility = View.VISIBLE
                },
                onSuccess = { jobs: List<Job> ->
                    binding.pbPopularJobs.visibility = View.INVISIBLE
                    popularJobsListAdapter.jobs = jobs.take(5)
                },
                onLoading = {
                    binding.pbPopularJobs.visibility = View.VISIBLE
                }
            )
        }
        jobsViewModel.recentPostedJobs.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    Functions.showErrorWithToast(
                        requireContext(),
                        tag,
                        error
                    )
                    binding.pbRecentPostedJobs.visibility = View.VISIBLE
                },
                onSuccess = { jobs: List<Job> ->
                    binding.pbRecentPostedJobs.visibility = View.INVISIBLE
                    recentJobsListAdapter.jobs = jobs.take(5)
                },
                onLoading = {
                    binding.pbRecentPostedJobs.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun addFavoritesObserver(
        jobId: String,
        onBinding: (Int) -> Unit,
    ) {
        userViewModel.favorites.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = {},
                onSuccess = {
                    val isFavorited: Boolean? = userViewModel.isItemFavorite(jobId)
                    val novoDrawable = if (isFavorited == true) {
                        R.drawable.ic_favorites_filled_red_24
                    } else {
                        R.drawable.ic_favorite_24
                    }
                    onBinding(novoDrawable)
                },
            )
        }
    }

    private fun initToolbar() {
        toolbar = binding.includeToolbar.toolbar
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}