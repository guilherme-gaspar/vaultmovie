package com.guilhermegaspar.vaultmovie.domain.repository

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie

interface MovieRepository {

    suspend fun getPopularMovies(): List<PopularMovie>

    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    suspend fun saveFavoriteMovie(favoriteMovie: FavoriteMovie)

    suspend fun deleteFavoriteMovie(id: Int)
}