package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.data.database.AppCoinDao
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.repository.CoinRepositoryImpl
import com.example.myapplication.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
     @CoinAppScope
    @Binds
    fun bindCoinRepository(impl : CoinRepositoryImpl) : CoinRepository


    companion object {

        @CoinAppScope
        @Provides
        fun provideAppCoinDao(application: Application): AppCoinDao {
            return AppDatabase.getInstance(application).appCoinDao()
        }

        @CoinAppScope
        @Provides
        fun provideApiService() : ApiService{
            return ApiFactory.apiService
        }
    }
}