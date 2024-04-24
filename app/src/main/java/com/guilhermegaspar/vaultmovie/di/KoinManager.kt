package com.guilhermegaspar.vaultmovie.di

import com.guilhermegaspar.vaultmovie.domain.usecase.GetPopularMoviesUseCase
import com.guilhermegaspar.vaultmovie.presentation.main.viewmodel.MainViewModel
import com.guilhermegaspar.vaultmovie.data.remote.MovieService
import com.guilhermegaspar.vaultmovie.network.di.getNetworkModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object KoinManager {
    fun loadAllModules(): List<Module> {
        return listOf(
            getNetworkModule(), getMovieModule()
        )
    }
}

fun getMovieModule() = module {
    viewModel {
        MainViewModel(
            useCase = GetPopularMoviesUseCase(
                get<Retrofit>().create(
                    MovieService::class.java
                )
            )
        )
    }
}
