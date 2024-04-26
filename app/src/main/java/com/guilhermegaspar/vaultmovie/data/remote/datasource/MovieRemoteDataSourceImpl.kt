package com.guilhermegaspar.vaultmovie.data.remote.datasource

import com.guilhermegaspar.vaultmovie.data.remote.model.MovieService

class MovieRemoteDataSourceImpl(private val service: MovieService) : MovieRemoteDataSource {
    override suspend fun getPopularMovies() = service.getPopularMovies()
}
