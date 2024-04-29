package com.guilhermegaspar.vaultmovie.data.cache.datasource

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie


class MovieCacheDataSourceImpl : MovieCacheDataSource {

    private val favoriteMovies = ArrayList<FavoriteMovie>()
    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return favoriteMovies.toList()
    }

    override suspend fun saveFavoriteMovie(favoriteMovie: FavoriteMovie) {
        favoriteMovies.add(favoriteMovie)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        favoriteMovies.removeIf { it.id == id }
    }
}