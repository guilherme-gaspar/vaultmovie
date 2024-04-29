package com.guilhermegaspar.vaultmovie.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermegaspar.vaultmovie.domain.usecase.GetPopularMoviesUseCase
import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie
import com.guilhermegaspar.vaultmovie.domain.model.toFavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.usecase.DeleteFavoriteMovieUseCase
import com.guilhermegaspar.vaultmovie.domain.usecase.GetFavoriteMoviesUseCase
import com.guilhermegaspar.vaultmovie.domain.usecase.SaveFavoriteMovieUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : ViewModel() {

    private val selectedHomeCategory = MutableStateFlow(HomeCategory.Discover)
    private val refreshing = MutableStateFlow(false)
    private val followedMovies = MutableStateFlow(persistentListOf<FavoriteMovie>())
    private val popularMovies = MutableStateFlow(persistentListOf<PopularMovie>())
    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                selectedHomeCategory,
                refreshing,
                followedMovies,
                popularMovies
            ) { selectedHomeCategory, _, _, _ ->
                MainViewState(
                    followedMovies = getFavoriteMoviesUseCase(),
                    popularMovies = getPopularMoviesUseCase(),
                    selectedHomeCategory = selectedHomeCategory,
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _state.value = it
            }
        }


    }

    fun onHomeCategorySelected(category: HomeCategory) {
        selectedHomeCategory.value = category
    }

    fun onToggleMovieFollowed(movie: PopularMovie) {
        viewModelScope.launch {
            saveFavoriteMovieUseCase(movie.toFavoriteMovie())
        }
    }

    fun onToggleMovieUnfollowed(movieId: Int) {
        viewModelScope.launch {
            deleteFavoriteMovieUseCase.invoke(movieId)
        }
    }
}

data class MainViewState(
    val followedMovies: PersistentList<FavoriteMovie> = persistentListOf(),
    val popularMovies: PersistentList<PopularMovie> = persistentListOf(),
    val selectedHomeCategory: HomeCategory = HomeCategory.Discover
)

enum class HomeCategory {
    Library, Discover
}