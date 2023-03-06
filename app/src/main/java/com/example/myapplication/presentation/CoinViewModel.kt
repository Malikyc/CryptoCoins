package com.example.myapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.CoinRepositoryImpl
import com.example.myapplication.domain.CoinInfo
import com.example.myapplication.domain.GetCoinInfoListUseCase
import com.example.myapplication.domain.GetCoinInfoUseCase
import com.example.myapplication.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val coinRepository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(coinRepository)

    private val getCoinInfoUseCase = GetCoinInfoUseCase(coinRepository)

    private val loadDataUseCase = LoadDataUseCase(coinRepository)

     val coinList = getCoinInfoListUseCase.invoke()

    private var _particularCoinInfo = MutableLiveData<CoinInfo>()
    val particularCoinInfo : LiveData<CoinInfo>
    get() = _particularCoinInfo

    init {
        loadData()
    }

    private fun loadData(){
            loadDataUseCase.invoke()
    }

    fun getCoinByFsym (fsym:String) {
        viewModelScope.launch {
            val coin = getCoinInfoUseCase.invoke(fsym)
            _particularCoinInfo.value = coin
        }
    }








}