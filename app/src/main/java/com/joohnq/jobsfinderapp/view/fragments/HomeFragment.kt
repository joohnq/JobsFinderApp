package com.joohnq.jobsfinderapp.view.fragments

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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.adapters.PopularJobsListAdapter
import com.joohnq.jobsfinderapp.adapters.RecentJobsListAdapter
import com.joohnq.jobsfinderapp.databinding.CustomBottomSheetBinding
import com.joohnq.jobsfinderapp.databinding.FragmentHomeBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val tag = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val jobsViewModel: JobsViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var rvPopularPost: RecyclerView
    private lateinit var rvRecentPost: RecyclerView

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
                onSuccess = { data ->
                    binding.pbPopularJobs.visibility = View.INVISIBLE
                    val jobsList: List<Job> = data
                    rvPopularPost.adapter = PopularJobsListAdapter(jobsList.take(5))
                },
                onLoading = {
                    binding.pbRecentPostedJobs.visibility = View.VISIBLE
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
                onSuccess = { data ->
                    binding.pbRecentPostedJobs.visibility = View.INVISIBLE
                    val jobsList: List<Job> = data
                    rvRecentPost.adapter = RecentJobsListAdapter(jobsList.take(5))
                },
                onLoading = {
                    binding.pbRecentPostedJobs.visibility = View.VISIBLE
                }
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