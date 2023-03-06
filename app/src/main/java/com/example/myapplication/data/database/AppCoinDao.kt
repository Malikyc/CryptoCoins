package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppCoinDao {
    @Query("SELECT * FROM coin_info_list ORDER BY lastUpdate DESC")
     fun getCoinList():LiveData<List<CoinInfoDbModel>>

    @Query("SELECT * FROM coin_info_list WHERE fromSymbol == :fSym")
    suspend fun getParticularCoin(fSym : String):CoinInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(listOfCoinInfo : List<CoinInfoDbModel>)




}