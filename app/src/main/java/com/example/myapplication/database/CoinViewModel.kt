package com.example.myapplication.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.api.ApiFactory
import com.example.myapplication.pojo.CoinPriceInfo
import com.example.myapplication.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val database : AppDatabase = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceList = database.appCoinDao().getCoinList()

    init {
        loadData()
    }


    fun getDetailedCoinInfo(fsym:String) : LiveData<CoinPriceInfo>{
        return database.appCoinDao().getParticularCoin(fsym)
    }


    private fun loadData(){
        val disposable = ApiFactory.apiService.getCoinInfoListOfData()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",")!! }
            .flatMap { ApiFactory.apiService.getCoinPriceInfoRawData(fSYMS = it) }
            .map { transformData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                database.appCoinDao().insertData(it)
                Log.i("SeeingParams",it.toString())
            },{
                Log.i("SeeingParams",it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun transformData(coinPriceInfoRawData: CoinPriceInfoRawData) :
            List<CoinPriceInfo>{
        var listOfData = mutableListOf<CoinPriceInfo>()
        var jsonObject = coinPriceInfoRawData.coinInfoJsonObject
        if (jsonObject == null) return listOfData
        var rawKeySet = jsonObject.keySet()
        for (keySet in rawKeySet) {
            var currencyJsonObject = jsonObject.getAsJsonObject(keySet)
            var currentKeySet = currencyJsonObject.keySet()
            for (currentKey in currentKeySet) {
                val coinPriceInfo = Gson().fromJson(
                    currencyJsonObject.getAsJsonObject(currentKey), CoinPriceInfo::class.java
                )
                listOfData.add(coinPriceInfo)
            }
        }
        return listOfData

    }




    override fun onCleared() {
        super.onCleared()
    }


}