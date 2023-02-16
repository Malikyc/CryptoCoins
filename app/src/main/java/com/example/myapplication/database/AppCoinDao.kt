package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.pojo.CoinPriceInfo

@Dao
interface AppCoinDao {
    @Query("SELECT * FROM coin_info_list ORDER BY lastUpdate DESC")
    fun getCoinList():LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM coin_info_list WHERE fromSymbol == :fSym")
    fun getParticularCoin(fSym : String):LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(listOfCoinInfo : List<CoinPriceInfo>)




}