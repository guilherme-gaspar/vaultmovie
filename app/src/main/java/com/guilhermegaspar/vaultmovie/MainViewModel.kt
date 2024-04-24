package com.guilhermegaspar.vaultmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermegaspar.vaultmovie.recipes.FavoriteMovie
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
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
    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                selectedHomeCategory,
                refreshing,
                followedMovies
            ) { selectedHomeCategory, _, followedMovies ->
                MainViewState(
                    followedMovies = useCase.invoke(),
                    selectedHomeCategory = selectedHomeCategory
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

    fun onTogglePodcastFollowed(movie: FavoriteMovie) {
        _state.update {
            it.copy(followedMovies = followedMovies.value.add(movie))
        }
    }
}

data class MainViewState(
    val followedMovies: PersistentList<FavoriteMovie> = persistentListOf(),
    val selectedHomeCategory: HomeCategory = HomeCategory.Discover
)