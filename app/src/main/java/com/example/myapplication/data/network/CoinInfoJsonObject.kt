package com.example.myapplication.data.network

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonObject (
    @SerializedName("RAW")

    @Expose
    val jsonObject: JsonObject ?= null
        )