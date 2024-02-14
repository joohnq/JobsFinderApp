package com.joohnq.jobsfinderapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joohnq.jobsfinderapp.databinding.FragmentDescriptionJobDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionJobDetailFragment(private val bindingDescription: FragmentDescriptionJobDetailBinding) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingDescription.root
    }
}