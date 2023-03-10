package com.example.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.CoinApp
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.di.CoinAppComponent
import com.example.myapplication.presentation.adapters.CoinAdapter
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as CoinApp).component
    }
    @Inject
    lateinit var viewModelFactory : CoinViewModelFactory


   private  val viewModel : CoinViewModel by lazy {
       ViewModelProvider(this,viewModelFactory)[CoinViewModel::class.java]
   }
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

   private val coinAdapter:CoinAdapter by lazy {
       CoinAdapter()
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.RecyclerView.adapter = coinAdapter
        binding.RecyclerView.animation = null
        setAdapterList()
        setOnClickListener()
    }


    private fun setAdapterList(){
        viewModel.coinList.observe(this){
            coinAdapter.coinInfoList = it
        }
    }

    private fun setOnClickListener(){
       coinAdapter.coinOnclickListener = {
           if(isLandOrientant()){
           val intent = DetailedCoinInfo.newIntent(this,it.fromSymbol)
           startActivity(intent)}
           else{
               val fragment = DetailedCoinInfoFragment.newInstance(it.fromSymbol)
               supportFragmentManager.beginTransaction()
                   .addToBackStack(null)
                   .replace(R.id.mainFragmentContainer,fragment)
                   .commit()
           }
       }
    }
   private fun isLandOrientant():Boolean{
       return binding.guideline == null
   }

}