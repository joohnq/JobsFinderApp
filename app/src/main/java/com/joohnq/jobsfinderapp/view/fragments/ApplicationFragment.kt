package com.joohnq.jobsfinderapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.joohnq.jobsfinderapp.adapters.ApplicationsListAdapter
import com.joohnq.jobsfinderapp.databinding.FragmentApplicationBinding
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationFragment : Fragment() {
    private val tag = "ApplicationFragment"
    private lateinit var binding: FragmentApplicationBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val jobViewModel: JobsViewModel by activityViewModels()
    private val applicationsListAdapter: ApplicationsListAdapter by lazy {
        ApplicationsListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observers()
    }

    private fun observers() {
        with(binding) {
            userViewModel.applications.observe(viewLifecycleOwner) { state ->
                tvDontHave.visibility = View.GONE

                state.handleUiState(
                    onLoading = {
                        loadingLayoutFavorite.visibility = View.VISIBLE
                    },
                    onFailure = {
                        userViewModel.getUser()
                    },
                    onSuccess = { applications ->
                        loadingLayoutFavorite.visibility = View.GONE
                        applications?.run {
                            if (isEmpty()) {
                                tvDontHave.visibility = View.VISIBLE
                                rvApplications.visibility = View.GONE
                            }
//                            lifecycleScope.launch {
//                                val jobDetail = jobViewModel.getJobDetail(id)
//                            }
                        }
                    }
                )
            }
            userViewModel.applicationDetails.observe(viewLifecycleOwner) { state ->
                state.handleUiState(
                    onLoading = {
                        loadingLayoutFavorite.visibility = View.VISIBLE
                        tvDontHave.visibility = View.GONE
                    },
                    onFailure = {
                        tvDontHave.visibility = View.GONE
                        userViewModel.getUser()
                    },
                    onSuccess = { applicationsDetails ->
                        loadingLayoutFavorite.visibility = View.GONE
                        applicationsDetails?.run {
                            if (isEmpty()) {
                                tvDontHave.visibility = View.VISIBLE
                                rvApplications.visibility = View.GONE
                            }
                            applicationsListAdapter.applications = this
                        }
                    }
                )
            }
        }
    }

    private fun initRv() {
        with(binding) {
            rvApplications.layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            rvApplications.adapter = applicationsListAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }
}