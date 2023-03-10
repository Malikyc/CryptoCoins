package com.example.myapplication.di

import androidx.lifecycle.ViewModel
import com.example.myapplication.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {
    @Binds
    @VIewModelKey(CoinViewModel::class)
    @IntoMap
    fun bindCoinViewModel(coinViewModel: CoinViewModel) : ViewModel
}