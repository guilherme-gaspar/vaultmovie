package com.guilhermegaspar.vaultmovie.domain.usecase

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.repository.MovieRepository

class SaveFavoriteMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(favoriteMovie: FavoriteMovie) {
        return repository.saveFavoriteMovie(favoriteMovie)
    }
}