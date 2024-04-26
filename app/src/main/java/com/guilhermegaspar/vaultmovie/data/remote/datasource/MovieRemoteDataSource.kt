package com.guilhermegaspar.vaultmovie.data.remote.datasource

import com.guilhermegaspar.vaultmovie.data.remote.model.DataPopularMovieResponse

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(): DataPopularMovieResponse
}