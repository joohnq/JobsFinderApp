package com.joohnq.jobsfinderapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.adapters.VpAdapter
import com.joohnq.jobsfinderapp.databinding.FragmentCompanyJobDetailBinding
import com.joohnq.jobsfinderapp.databinding.FragmentDescriptionJobDetailBinding
import com.joohnq.jobsfinderapp.databinding.ShowJobBottomSheetBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.JobApplyActivity
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailFragment(private val job: Job) : BottomSheetDialogFragment() {
    private lateinit var binding: ShowJobBottomSheetBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var bindingDescription: FragmentDescriptionJobDetailBinding
    private lateinit var bindingCompany: FragmentCompanyJobDetailBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCustomBottomSheet(job)
        bindingCompany = bindCompany(job)
        bindingDescription = bindDescription(job)
        viewPager2.adapter = VpAdapter(requireActivity(), bindingCompany, bindingDescription)
        configTabLayoutMediator()
    }

    private fun initJobApplyActivity(id: String) {
        val intent = Intent(requireContext(), JobApplyActivity::class.java)
        intent.putExtra("jobId", id)
        startActivity(intent)
    }

    private fun addFavoritesObserver(
        jobId: String,
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
                    bindingDescription.btnFavoriteJob.setImageResource(novoDrawable)
                },
            )
        }
    }

    private fun addApplicationObserver(
        jobId: String,
    ) {
        userViewModel.applications.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onSuccess = {
                    val isApplied: Boolean? = userViewModel.isItemApplication(jobId)
                    if (isApplied != null && isApplied) {
                        val bg = resources.getColor(R.color.green, null)
                        val text = resources.getString(R.string.already_applied)
                        val isEnabled = false
                        bindingDescription.btnApply.setBackgroundColor(bg)
                        bindingDescription.btnApply.text = text
                        bindingDescription.btnApply.isEnabled = isEnabled
                    } else {
                        bindingDescription.btnApply.setBackgroundColor(
                            resources.getColor(
                                R.color.green_204646,
                                null
                            )
                        )
                    }
                },
            )
        }
    }

    private fun configTabLayoutMediator() {
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.description)
                }

                1 -> {
                    tab.text = getString(R.string.company)
                }
            }
        }.attach()
    }

    private fun bindDescription(job: Job): FragmentDescriptionJobDetailBinding {
        val descriptionBinding: FragmentDescriptionJobDetailBinding =
            FragmentDescriptionJobDetailBinding.inflate(layoutInflater)
        with(descriptionBinding) {
            addFavoritesObserver(job.id)
            addApplicationObserver(job.id)
            tvDescriptionTab.text = job.description
            btnApply.setOnClickListener {
                initJobApplyActivity(job.id)
            }
            btnFavoriteJob.setOnClickListener {
                userViewModel.handleJobIdFavorite(job.id)
            }
        }
        return descriptionBinding
    }

    private fun bindCompany(job: Job): FragmentCompanyJobDetailBinding {
        val companyBinding: FragmentCompanyJobDetailBinding =
            FragmentCompanyJobDetailBinding.inflate(layoutInflater)
        with(companyBinding) {
            tvCompanyAboutTab.text = job.company.about
            tvCompanyNameTab.text = job.company.name
            tvCompanyReviewTab.text = job.company.reviews
            Glide.with(imgCompanyLogoTab).load(job.company.logoUrl).into(imgCompanyLogoTab)
        }
        return companyBinding
    }

    private fun bindCustomBottomSheet(
        job: Job,
    ) {
        tabLayout = binding.tabLayoutDetail
        viewPager2 = binding.viewPagerDetail
        with(binding) {
            tvJobTitleDetail.text = job.title
            tvLocationDetail.text = job.location
            tvTypeDetail.text = job.type
            with(job.salary) {
                val salary = "$symbol$entry - $end/$time"
                tvSallaryDetail.text = salary
            }
            tvCompanyNameDetail.text = job.company.name
            Glide.with(imgCompanyLogoDetail).load(job.company.logoUrl).into(imgCompanyLogoDetail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowJobBottomSheetBinding.inflate(inflater, null, false)
        return binding.root
    }
}