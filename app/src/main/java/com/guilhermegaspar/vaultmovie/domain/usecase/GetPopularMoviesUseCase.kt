package com.guilhermegaspar.vaultmovie.domain.usecase

import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie
import com.guilhermegaspar.vaultmovie.domain.repository.MovieRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class GetPopularMoviesUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(): PersistentList<PopularMovie> {
        return repository.getPopularMovies().toPersistentList()
    }
}