package com.guilhermegaspar.vaultmovie

import android.util.Log
import com.guilhermegaspar.vaultmovie.data.remote.MovieService
import com.guilhermegaspar.vaultmovie.recipes.FavoriteMovie
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class GetPopularMoviesUseCase(val service: MovieService) {

    suspend operator fun invoke(): PersistentList<FavoriteMovie> {
        val movies = service.getPopularMovies()

        Log.i("Teste movies", "Quais os movies? ${movies.results[4]}")

        return movies.results.map {
            FavoriteMovie(
                id = it.id.toString(),
                imageUrl = "https://image.tmdb.org/t/p/original${it.backdropPath}",
                title = it.title
            )
        }.toPersistentList()
    }
}