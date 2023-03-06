package com.example.myapplication.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CoinInfoListOfData (
    @SerializedName("Data")
    @Expose
   val data: List<CoinNameContainer>? = null
        )