package com.joohnq.job_ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.joohnq.job_ui.databinding.FragmentCompanyJobDetailBinding
import com.joohnq.job_ui.databinding.FragmentDescriptionJobDetailBinding
import com.joohnq.job_ui.fragments.CompanyJobDetailFragment
import com.joohnq.job_ui.fragments.DescriptionJobDetailFragment

class ViewPagerAdapter(
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