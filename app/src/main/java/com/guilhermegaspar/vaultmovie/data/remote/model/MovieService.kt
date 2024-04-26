package com.guilhermegaspar.vaultmovie.data.remote.model

import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): DataPopularMovieResponse
}