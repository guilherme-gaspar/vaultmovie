package com.guilhermegaspar.vaultmovie.domain.repository

import com.guilhermegaspar.vaultmovie.domain.model.PopularMovie

interface MovieRepository {

    suspend fun getPopularMovies(): List<PopularMovie>
}