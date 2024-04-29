package com.guilhermegaspar.vaultmovie.domain.usecase

import com.guilhermegaspar.vaultmovie.domain.repository.MovieRepository

class DeleteFavoriteMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int) {
        return repository.deleteFavoriteMovie(movieId)
    }
}