package com.guilhermegaspar.vaultmovie.data.remote.model


import com.google.gson.annotations.SerializedName

data class DataPopularMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<PopularMovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)