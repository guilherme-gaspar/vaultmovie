package com.guilhermegaspar.vaultmovie.data.remote

import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): DataMovieResponse
}