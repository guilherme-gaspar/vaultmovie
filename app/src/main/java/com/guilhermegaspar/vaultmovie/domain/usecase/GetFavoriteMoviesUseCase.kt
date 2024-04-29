package com.guilhermegaspar.vaultmovie.domain.usecase

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.repository.MovieRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class GetFavoriteMoviesUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(): PersistentList<FavoriteMovie> {
        return repository.getFavoriteMovies().toPersistentList()
    }
}