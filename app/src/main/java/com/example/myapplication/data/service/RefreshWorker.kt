package com.example.myapplication.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context,workerParameters) {

    private val dbDao = AppDatabase.getInstance(context).appCoinDao()

    private val mapper = CoinInfoMapper()

    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        while (true) {
            val listOfData = apiService.getCoinInfoListOfData()
            val names = mapper.mapListOfDataToString(listOfData)
            val jsonObject = apiService.getCoinInfoJsonObject(names)
            val listOfCoins = mapper.mapJsonObjectToList(jsonObject)
            val dbModelList = listOfCoins.map {
                mapper.mapDtoToDbModel(it)
            }
            dbDao.insertData(dbModelList)
            delay(10000)
        }
    }
    companion object{
        const val WORKER_NAME = "worker_name"

        fun makeRequest() : OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<RefreshWorker>()
                .build()
        }
    }
}