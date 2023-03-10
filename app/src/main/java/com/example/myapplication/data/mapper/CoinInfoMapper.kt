package com.example.myapplication.data.mapper

import com.example.myapplication.data.database.CoinInfoDbModel
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.data.network.CoinInfoDto
import com.example.myapplication.data.network.CoinInfoJsonObject
import com.example.myapplication.data.network.CoinInfoListOfData
import com.example.myapplication.data.utils.convertTime
import com.example.myapplication.domain.CoinInfo
import com.google.gson.Gson
import javax.inject.Inject

class CoinInfoMapper @Inject constructor() {
    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto) : CoinInfoDbModel{
        return CoinInfoDbModel(
            fromSymbol= coinInfoDto.fromSymbol,
            toSymbol=coinInfoDto.toSymbol,
            price=coinInfoDto.price,
            lastUpdate=coinInfoDto.lastUpdate,
            highDay =coinInfoDto.highDay,
            lowDay = coinInfoDto.lowDay,
            lastMarket = coinInfoDto.lastMarket,
            imageUrl = coinInfoDto.imageUrl

        )
    }

    fun mapDbModelToEntity(coinInfoDbModel: CoinInfoDbModel): CoinInfo{
        return CoinInfo(
            fromSymbol= coinInfoDbModel.fromSymbol,
            toSymbol=coinInfoDbModel.toSymbol,
            price=coinInfoDbModel.price,
            lastUpdate=getFormatedLastUpdate(coinInfoDbModel.lastUpdate),
            highDay =coinInfoDbModel.highDay,
            lowDay = coinInfoDbModel.lowDay,
            lastMarket = coinInfoDbModel.lastMarket,
            imageUrl = getFullImgUrl(coinInfoDbModel.imageUrl?:"")
        )
    }

    fun mapListOfDataToString(coinInfoListOfData: CoinInfoListOfData):String{
        return coinInfoListOfData.data?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapListOfDbModelsToListOfEntities(dbModels:List<CoinInfoDbModel>):
            List<CoinInfo>{
        return dbModels.map {
            mapDbModelToEntity(it) }
    }

    fun getFormatedLastUpdate(lastUpdate : Long?):String{
        return convertTime(lastUpdate)
    }
    fun getFullImgUrl(imageUrl :String):String{
        return ApiFactory.BASE_IMAGE_URl + imageUrl
    }
    fun mapJsonObjectToList(coinInfoJsonObject: CoinInfoJsonObject) :
            List<CoinInfoDto>{
        var listOfData = mutableListOf<CoinInfoDto>()
        var jsonObject = coinInfoJsonObject.jsonObject
        if (jsonObject == null) return listOfData
        var rawKeySet = jsonObject.keySet()
        for (keySet in rawKeySet) {
            var currencyJsonObject = jsonObject.getAsJsonObject(keySet)
            var currentKeySet = currencyJsonObject.keySet()
            for (currentKey in currentKeySet) {
                val coinInfo = Gson().fromJson(
                    currencyJsonObject.getAsJsonObject(currentKey), CoinInfoDto::class.java
                )
                listOfData.add(coinInfo)
            }
        }
        return listOfData
    }
}
