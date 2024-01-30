package com.joohnq.jobsfinderapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.adapters.PopularJobsListAdapter
import com.joohnq.jobsfinderapp.adapters.RecentJobsListAdapter
import com.joohnq.jobsfinderapp.databinding.CustomBottomSheetBinding
import com.joohnq.jobsfinderapp.databinding.FragmentHomeBinding
import com.joohnq.jobsfinderapp.databinding.PopularJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.PresentationActivity
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val tag = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val jobsViewModel: JobsViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var rvPopularPost: RecyclerView
    private lateinit var rvRecentPost: RecyclerView
    private val popularJobsListAdapter by lazy {
        PopularJobsListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId, binding)
            },
            onFavourite = { jobId: String ->
                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }

    private val recentJobsListAdapter by lazy {
        RecentJobsListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRvs()
        observer()
        bindButtons()
    }

    private fun bindButtons() {
        binding.imgBtnFilters.setOnClickListener {
            showBottomSheetDialog()
        }

        binding.includeToolbar.tvHello.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.logout()
                val intent = Intent(requireContext(), PresentationActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun showBottomSheetDialog() {
        val sheetBinding: CustomBottomSheetBinding =
            CustomBottomSheetBinding.inflate(layoutInflater)
        val bottomSheet: BottomSheetDialog =
            Functions.customBottomSheet(requireContext(), sheetBinding)
        bottomSheet.show()
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
                    val userName = data?.name?.let { Functions.getTwoWords(it) }
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
                    popularJobsListAdapter.jobs = jobs
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
                    recentJobsListAdapter.jobs = jobs
                },
                onLoading = {
                    binding.pbRecentPostedJobs.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun addFavoritesObserver(
        jobId: String,
        binding: PopularJobItemBinding
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
                    binding.btnFavorite.setImageResource(novoDrawable)
                },
                onLoading = {}
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