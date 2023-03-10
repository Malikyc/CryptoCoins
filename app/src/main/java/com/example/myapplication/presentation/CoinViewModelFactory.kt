package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.GetCoinInfoListUseCase
import com.example.myapplication.domain.GetCoinInfoUseCase
import com.example.myapplication.domain.LoadDataUseCase
import javax.inject.Inject
import javax.inject.Provider

class CoinViewModelFactory @Inject constructor(
    private val mapOfViewModelsProviders : @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return mapOfViewModelsProviders[modelClass]?.get() as T
    }
}