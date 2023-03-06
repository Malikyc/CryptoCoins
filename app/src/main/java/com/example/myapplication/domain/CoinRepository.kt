package com.example.myapplication.domain

import androidx.lifecycle.LiveData

interface CoinRepository {
    fun getCoinInfoList() : LiveData<List<CoinInfo>>

    suspend fun getCoinInfo(fromSymbol:String) : CoinInfo

     fun loadData()
}