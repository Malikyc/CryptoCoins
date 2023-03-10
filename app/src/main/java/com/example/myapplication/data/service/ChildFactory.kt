package com.example.myapplication.data.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildFactory {
    fun create(context: Context,
    workerParameters: WorkerParameters) : ListenableWorker
}