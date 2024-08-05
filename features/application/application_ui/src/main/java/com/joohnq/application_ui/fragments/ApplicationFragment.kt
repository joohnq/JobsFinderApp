package com.joohnq.application_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.joohnq.application_ui.adapter.ApplicationsListAdapter
import com.joohnq.application_ui.databinding.FragmentApplicationBinding
import com.joohnq.application_ui.viewmodel.ApplicationsViewModel
import com.joohnq.core.helper.RecyclerViewHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationFragment: Fragment() {
				private var _binding: FragmentApplicationBinding? = null
				private val binding get() = _binding!!
				private val applicationsViewModel: ApplicationsViewModel by activityViewModels()
				private val applicationsListAdapter: ApplicationsListAdapter by lazy { ApplicationsListAdapter() }

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.initRv()
								binding.observers()
				}

				private fun FragmentApplicationBinding.observers() {
//								applicationsViewModel.applications.observe(viewLifecycleOwner) { state ->
//												applicationsListAdapter.setState(state.toRecyclerViewState())
//								}
				}

				private fun FragmentApplicationBinding.initRv() {
								RecyclerViewHelper.initVerticalWithoutScroll(
												rvApplications,
												applicationsListAdapter
								)
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?,
								savedInstanceState: Bundle?
				): View {
								_binding = FragmentApplicationBinding.inflate(inflater, container, false)
								return binding.root
				}
}