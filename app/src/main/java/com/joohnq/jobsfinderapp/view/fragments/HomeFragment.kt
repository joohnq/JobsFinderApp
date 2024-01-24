package com.joohnq.jobsfinderapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.adapters.PopularJobsListAdapter
import com.joohnq.jobsfinderapp.adapters.RecentJobsListAdapter
import com.joohnq.jobsfinderapp.databinding.FragmentHomeBinding
import com.joohnq.jobsfinderapp.util.UiState
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val jobsViewModel: JobsViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var rvPopularPost: RecyclerView
    private lateinit var rvRecentPost: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRvs()
        observer()
    }

    private fun initRvs() {
        rvPopularPost = binding.rvPopularPost
        rvRecentPost = binding.rvRecentPost
        rvPopularPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecentPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun observer() {
        userViewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> {
                    state.error?.let {
                        Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                    }
                }

                is UiState.Success -> {
                    val userPhoto = state.data?.imageUrl
                    val profileImage = binding.includeToolbar.profileImage
                    if (!userPhoto.isNullOrEmpty()) {
                        Glide
                            .with(requireContext())
                            .load(userPhoto)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_generic)
                            .into(profileImage)
                    }
                }

                else -> Unit
            }
        }
        jobsViewModel.popularJobs.observe(viewLifecycleOwner){
            rvPopularPost.adapter = PopularJobsListAdapter(it.take(5))
        }

        jobsViewModel.recentPostedJobs.observe(viewLifecycleOwner){
            rvRecentPost.adapter = RecentJobsListAdapter(it.take(5))
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
        userViewModel.getUserData()
        return binding.root
    }
}