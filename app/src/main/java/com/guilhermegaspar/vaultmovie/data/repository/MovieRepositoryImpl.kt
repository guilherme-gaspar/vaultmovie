package com.guilhermegaspar.vaultmovie.data.repository

import com.guilhermegaspar.vaultmovie.data.cache.datasource.MovieCacheDataSource
import com.guilhermegaspar.vaultmovie.data.remote.datasource.MovieRemoteDataSource
import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie
import com.guilhermegaspar.vaultmovie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val cacheDataSource: MovieCacheDataSource
) : MovieRepository {
    override suspend fun getPopularMovies(): List<PopularMovie> {
        return remoteDataSource.getPopularMovies().results.map {
            PopularMovie(it.id, "https://image.tmdb.org/t/p/original${it.backdropPath}", it.title)
        }
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return cacheDataSource.getFavoriteMovies().map {
            FavoriteMovie(0, "", "")
        }
    }

}