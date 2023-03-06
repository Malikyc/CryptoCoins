package com.example.myapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.service.RefreshWorker
import com.example.myapplication.domain.CoinInfo
import com.example.myapplication.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val dbDao = AppDatabase.getInstance(application).appCoinDao()

    private val mapper = CoinInfoMapper()

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
         val workManager = WorkManager.getInstance(application)
         workManager.enqueueUniqueWork(
             RefreshWorker.WORKER_NAME,
             ExistingWorkPolicy.REPLACE,
             RefreshWorker.makeRequest()
         )
    }


}