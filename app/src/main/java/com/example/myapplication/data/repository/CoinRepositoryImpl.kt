package com.example.myapplication.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.myapplication.data.database.AppCoinDao
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.service.RefreshWorker
import com.example.myapplication.domain.CoinInfo
import com.example.myapplication.domain.CoinRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val application: Application,
                                             private val dbDao : AppCoinDao,
                                             private val mapper : CoinInfoMapper) : CoinRepository {


    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(
            dbDao.getCoinList()
        ){
            mapper.mapListOfDbModelsToListOfEntities(it)
        }
    }

    override  suspend fun getCoinInfo(fromSymbol: String): CoinInfo {
        return mapper.mapDbModelToEntity(dbDao.getParticularCoin(fromSymbol))
    }

     override fun loadData(){
         Log.i("ITWORKS","YEAAAAH")
         val workManager = WorkManager.getInstance(application)
         workManager.enqueueUniqueWork(
             RefreshWorker.WORKER_NAME,
             ExistingWorkPolicy.REPLACE,
             RefreshWorker.makeRequest()
         )
    }


}