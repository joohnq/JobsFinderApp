package com.joohnq.jobsfinderapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joohnq.jobsfinderapp.databinding.FragmentCompanyJobDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyJobDetailFragment(private val bindingCompany: FragmentCompanyJobDetailBinding) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingCompany.root
    }
}