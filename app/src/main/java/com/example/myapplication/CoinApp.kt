package com.example.myapplication

import android.app.Application
import androidx.work.Configuration
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.service.RefreshWorkerFactory
import com.example.myapplication.di.DaggerCoinAppComponent
import dagger.internal.DaggerGenerated
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {
    @Inject
    lateinit var factory: RefreshWorkerFactory

  val component by lazy {
   DaggerCoinAppComponent.factory().create(this)
  }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().
                setWorkerFactory(factory)
            .build()
    }
}