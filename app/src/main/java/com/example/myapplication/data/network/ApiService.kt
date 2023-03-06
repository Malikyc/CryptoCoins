package com.example.myapplication.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val QUERY_PARAM_LIMIT : String = "limit"
        const val QUERY_PARAM_TO_SIMBOL : String = "tsym"
        const val QUERY_PARAM_API_KEY = "api_key"
        const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        const val  QUERY_PARAM_TO_SYMBOLS = "tsyms"
        const val CURRENT = "USD"
    }
    @GET("top/totalvolfull")
    suspend fun getCoinInfoListOfData (
        @Query(QUERY_PARAM_LIMIT) limit : Int = 10,
        @Query(QUERY_PARAM_TO_SIMBOL) tSYM : String = CURRENT,
        @Query(QUERY_PARAM_API_KEY) api_key : String = "5681d9aca5c00c698a9c4af988b0ef1a84d24f794a96e873fe650a8b22b3b3b2"
    ): CoinInfoListOfData

    @GET("pricemultifull")
   suspend fun getCoinInfoJsonObject(
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSYMS : String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSYMS : String = CURRENT

   ):CoinInfoJsonObject

}