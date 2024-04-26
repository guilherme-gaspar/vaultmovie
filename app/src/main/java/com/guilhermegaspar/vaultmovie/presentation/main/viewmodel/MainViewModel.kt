package com.guilhermegaspar.vaultmovie.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermegaspar.vaultmovie.domain.usecase.GetPopularMoviesUseCase
import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie
import com.guilhermegaspar.vaultmovie.domain.model.toFavoriteMovie
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: GetPopularMoviesUseCase) : ViewModel() {

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
                    followedMovies = useCase.invoke().map { it.toFavoriteMovie() }
                        .toPersistentList(),
                    selectedHomeCategory = selectedHomeCategory,
                    popularMovies = useCase.invoke()
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }


    }

    fun onHomeCategorySelected(category: HomeCategory) {
        selectedHomeCategory.value = category
    }

    fun onTogglePodcastFollowed(movie: PopularMovie) {
        _state.update {
            it.copy(followedMovies = followedMovies.value.add(movie.toFavoriteMovie()))
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