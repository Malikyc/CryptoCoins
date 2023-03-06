package com.example.myapplication.domain

class LoadDataUseCase(private val coinRepository: CoinRepository) {
     operator fun invoke(){
        coinRepository.loadData()
    }
}