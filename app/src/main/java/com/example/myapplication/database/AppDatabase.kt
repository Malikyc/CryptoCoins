package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private var db:AppDatabase? = null
        private const val DB_NAME = "coin_info_price_list"


        fun getInstance(context : Context) : AppDatabase{
            db?.let { return it }
            val instance : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME).build()
             return instance
        }
    }
    abstract fun appCoinDao() : AppCoinDao
}