package com.example.myapplication.domain

class LoadDataUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(){
        coinRepository.loadData()
    }
}