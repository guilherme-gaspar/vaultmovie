package com.guilhermegaspar.vaultmovie.data.cache.datasource

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie


interface MovieCacheDataSource {

    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    suspend fun saveFavoriteMovie(favoriteMovie: FavoriteMovie)

    suspend fun deleteFavoriteMovie(id: Int)
}