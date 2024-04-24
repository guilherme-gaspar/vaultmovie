package com.guilhermegaspar.vaultmovie.data.remote


import com.google.gson.annotations.SerializedName

data class DataMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)