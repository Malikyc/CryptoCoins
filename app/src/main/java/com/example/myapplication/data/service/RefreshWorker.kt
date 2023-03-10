package com.example.myapplication.data.service

import android.content.Context
import androidx.work.*
import com.example.myapplication.data.database.AppCoinDao
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val dbDao: AppCoinDao,
    private val mapper: CoinInfoMapper,
    private val apiService: ApiService
) : CoroutineWorker(context,workerParameters) {



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

    class Factory @Inject constructor(
        private val dbDao: AppCoinDao,
        private val mapper: CoinInfoMapper,
        private val apiService: ApiService
    ):ChildFactory{
        override fun create(context: Context, workerParameters: WorkerParameters): ListenableWorker {
            return RefreshWorker(context,workerParameters,dbDao,mapper,apiService)
        }
    }

}