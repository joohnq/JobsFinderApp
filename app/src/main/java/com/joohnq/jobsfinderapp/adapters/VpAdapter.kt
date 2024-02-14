package com.joohnq.jobsfinderapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.joohnq.jobsfinderapp.databinding.FragmentCompanyJobDetailBinding
import com.joohnq.jobsfinderapp.databinding.FragmentDescriptionJobDetailBinding
import com.joohnq.jobsfinderapp.view.fragments.CompanyJobDetailFragment
import com.joohnq.jobsfinderapp.view.fragments.DescriptionJobDetailFragment

class VpAdapter(
    fragmentActivity: FragmentActivity,
    private val bindingCompany: FragmentCompanyJobDetailBinding,
    private val bindingDescription: FragmentDescriptionJobDetailBinding
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DescriptionJobDetailFragment(bindingDescription)
            }

            else -> {
                CompanyJobDetailFragment(bindingCompany)
            }
        }
    }
}