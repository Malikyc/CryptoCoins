package com.example.myapplication.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(private val coinRepository: CoinRepository) {
   suspend operator fun invoke(fromSymbol:String)=coinRepository.getCoinInfo(fromSymbol)

}