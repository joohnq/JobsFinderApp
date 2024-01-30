package com.joohnq.jobsfinderapp.view.fragments

import FavoritesListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentFavouritesBinding
import com.joohnq.jobsfinderapp.databinding.PopularJobItemBinding
import com.joohnq.jobsfinderapp.databinding.SearchJobItemBinding
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val tag = "FavoritesFragment"
    private lateinit var binding: FragmentFavouritesBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val jobViewModel: JobsViewModel by activityViewModels()
    private val favoritesListAdapter: FavoritesListAdapter by lazy {
        FavoritesListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId, binding)
            },
            onFavourite = { jobId: String ->
                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }

    private fun addFavoritesObserver(
        jobId: String,
        binding: SearchJobItemBinding
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
                    binding.imgBtnFavorite.setImageResource(novoDrawable)
                },
                onLoading = {}
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observers()
    }

    private fun observers() {
        userViewModel.favorites.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onLoading = {},
                onFailure = {},
                onSuccess = { favorites ->
                    favorites?.run {
                        jobViewModel.getJobDetail(this)
                    }
                }
            )
        }

        userViewModel.favoritesDetails.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onLoading = {},
                onFailure = {},
                onSuccess = { favoriteDetails ->
                    favoriteDetails?.run {
                        favoritesListAdapter.jobs = this
                    }
                }
            )
        }
    }

    private fun initRv() {
        with(binding) {
            rvFavorites.layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            rvFavorites.adapter = favoritesListAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }
}