package com.joohnq.jobsfinderapp.view.fragments

import FavoritesListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentFavouritesBinding
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
                addFavoritesObserver(jobId){drawable ->
                    binding.imgBtnFavorite.setImageResource(drawable)
                }
            },
            onFavourite = { jobId: String ->
                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }

    private fun addFavoritesObserver(
        jobId: String,
        onBinding: (Int) -> Unit
    ) {
        userViewModel.favorites.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onSuccess = {
                    this.binding.loadingLayoutFavorite.visibility = View.GONE
                    val isFavorited: Boolean? = userViewModel.isItemFavorite(jobId)
                    val novoDrawable = if (isFavorited == true) {
                        R.drawable.ic_favorites_filled_red_24
                    } else {
                        R.drawable.ic_favorite_24
                    }
                    onBinding(novoDrawable)
                },
                onLoading = {
                    this.binding.loadingLayoutFavorite.visibility = View.VISIBLE
                }
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observers()
    }

    private fun observers() {
        with(binding) {
            userViewModel.favorites.observe(viewLifecycleOwner) { state ->
                tvDontHave.visibility = View.GONE

                Functions.handleUiState(
                    state,
                    onLoading = {
                        loadingLayoutFavorite.visibility = View.VISIBLE
                    },
                    onFailure = {
                        userViewModel.getUserFromDatabase()
                    },
                    onSuccess = { favorites ->
                        loadingLayoutFavorite.visibility = View.GONE
                        favorites?.run {
                            if (isEmpty()) {
                                tvDontHave.visibility = View.VISIBLE
                               rvFavorites.visibility = View.GONE
                            }
                            jobViewModel.getJobDetail(this){
                                userViewModel.setFavoritesDetails(it)
                            }
                        }
                    }
                )
            }
            userViewModel.favoritesDetails.observe(viewLifecycleOwner) { state ->
                Functions.handleUiState(
                    state,
                    onLoading = {
                        loadingLayoutFavorite.visibility = View.VISIBLE
                        tvDontHave.visibility = View.GONE
                    },
                    onFailure = {
                        tvDontHave.visibility = View.GONE
                        userViewModel.getUserFromDatabase()
                    },
                    onSuccess = { favoriteDetails ->
                        loadingLayoutFavorite.visibility = View.GONE
                        favoriteDetails?.run {
                            if (isEmpty()) {
                                tvDontHave.visibility = View.VISIBLE
                                rvFavorites.visibility = View.GONE
                            }
                            favoritesListAdapter.jobs = this
                        }
                    }
                )
            }
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