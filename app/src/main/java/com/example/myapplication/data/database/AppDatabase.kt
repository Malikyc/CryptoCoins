package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinInfoDbModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private var db: AppDatabase? = null
        private const val DB_NAME = "coin_info_price_list"


        fun getInstance(context : Context) : AppDatabase {
            db?.let { return it }
            val instance : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration()
                .build()
             return instance
        }
    }
    abstract fun appCoinDao() : AppCoinDao
}