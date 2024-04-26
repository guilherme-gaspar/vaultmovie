package com.guilhermegaspar.vaultmovie.domain.usecase

import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPopularMoviesUseCaseTest() {

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()

    @Test
    fun whenGetPopularMoviesUseCaseWasCalledShouldReturnAListOfFavoriteMovies() = runTest {
        // Given
        val stub = listOf(FavoriteMovie("123", "123", "123")).toPersistentList()
        coEvery { getPopularMoviesUseCase() } returns stub

        // When
        val result = getPopularMoviesUseCase()

        // Then
        assertEquals(result, stub)
    }
}