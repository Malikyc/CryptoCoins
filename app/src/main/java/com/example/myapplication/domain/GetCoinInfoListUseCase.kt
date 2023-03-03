package com.example.myapplication.domain

import androidx.lifecycle.LiveData

class GetCoinInfoListUseCase(private val coinRepository: CoinRepository) {
     operator fun invoke() = coinRepository.getCoinInfoList()

}