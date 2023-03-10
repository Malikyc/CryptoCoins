package com.example.myapplication.di

import com.example.myapplication.data.service.ChildFactory
import com.example.myapplication.data.service.RefreshWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(RefreshWorker::class)
    fun bindRefreshWorkerFactory(impl:RefreshWorker.Factory) : ChildFactory
}