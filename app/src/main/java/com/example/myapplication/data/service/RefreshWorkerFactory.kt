package com.example.myapplication.data.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.myapplication.data.database.AppCoinDao
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiService
import javax.inject.Inject
import javax.inject.Provider

class RefreshWorkerFactory @Inject constructor(
    private val workFactoryProviders : @JvmSuppressWildcards Map<Class<out ListenableWorker>,Provider<ChildFactory>>
):WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
         when(workerClassName){
        RefreshWorker::class.qualifiedName -> {
           val factory = workFactoryProviders[RefreshWorker::class.java]?.get()
            return factory?.create(appContext,workerParameters)
        }
             else-> return null
         }
    }
}