package com.guilhermegaspar.vaultmovie.data.cache.datasource


interface MovieCacheDataSource {

    suspend fun getFavoriteMovies(): List<String>
}