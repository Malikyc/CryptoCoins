package com.example.myapplication.domain

class GetCoinInfoUseCase(private val coinRepository: CoinRepository) {
   suspend operator fun invoke(fromSymbol:String)=coinRepository.getCoinInfo(fromSymbol)

}