package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.CoinApp
import com.example.myapplication.presentation.DetailedCoinInfoFragment
import com.example.myapplication.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@CoinAppScope
@Component(modules = [DataModule::class,ViewModelModule::class,WorkerModule::class])
interface CoinAppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: DetailedCoinInfoFragment)

    fun inject(application : CoinApp)

    @Component.Factory
    interface Factory {
       fun create(@BindsInstance application: Application) : CoinAppComponent
    }
}
