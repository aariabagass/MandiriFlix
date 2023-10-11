package com.ariabagas.mandiriflix.ui.screen.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.FragmentSearchBinding
import com.ariabagas.mandiriflix.ui.adapter.GenreAdapter
import com.ariabagas.mandiriflix.ui.adapter.MovieAdapter
import com.ariabagas.mandiriflix.ui.base.BaseFragment
import com.ariabagas.mandiriflix.util.Type
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    val adapterMovies by lazy { MovieAdapter(isGrid = true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvGenres, binding.rvMovies
        )

        setupSearchView()
        setupGenres()

        collectFlows(
            listOf(
                ::collectMovieSearchResults, ::collectUiState
            )
        )
    }

    private fun setupGenres() {
        val movieGenreIds = resources.getIntArray(R.array.movie_genre_ids).toTypedArray()
        val movieGenreNames = resources.getStringArray(R.array.movie_genre_names)

        binding.rvGenres.adapter = GenreAdapter(Type.MOVIE).apply {
            submitList(movieGenreIds.zip(movieGenreNames))
        }
    }

    fun clearSearch() {
        viewModel.clearSearchResults()
        adapterMovies.submitList(null)
    }

    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                binding.rvMovies.scrollToPosition(0)
//                if (!query.isNullOrEmpty()) viewModel.fetchInitialSearch(query)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean = false
//        })
    }

    private suspend fun collectMovieSearchResults() {
        viewModel.movieResult.collect { movies ->
            adapterMovies.submitList(movies)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
        }
    }
}