package com.example.myapplication.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRawData (
    @SerializedName("RAW")
    @Expose
    val coinInfoJsonObject: JsonObject ?= null
        )