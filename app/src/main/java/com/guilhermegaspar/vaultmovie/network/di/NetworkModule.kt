package com.guilhermegaspar.vaultmovie.network.di

import com.guilhermegaspar.vaultmovie.network.config.RetrofitConfig
import org.koin.dsl.module

fun getNetworkModule() = module {
    single { RetrofitConfig.getInstance() }
}