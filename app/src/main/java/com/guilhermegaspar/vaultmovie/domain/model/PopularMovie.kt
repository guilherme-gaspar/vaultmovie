package com.guilhermegaspar.vaultmovie.domain.model

data class PopularMovie(val id: Int, val imageUrl: String, val title: String)

fun PopularMovie.toFavoriteMovie() = FavoriteMovie(id, imageUrl, title)