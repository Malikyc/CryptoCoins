package com.example.myapplication.di

import androidx.work.ListenableWorker
import com.example.myapplication.data.service.ChildFactory
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value : KClass<out ListenableWorker>)
