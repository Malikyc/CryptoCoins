package com.example.myapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.mapper.CoinInfoMapper
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.domain.CoinInfo
import com.example.myapplication.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application) : CoinRepository {

    private val dbDao = AppDatabase.getInstance(application).appCoinDao()

    private val mapper = CoinInfoMapper()

    private val apiService = ApiFactory.apiService

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

     override suspend fun loadData(){
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


}