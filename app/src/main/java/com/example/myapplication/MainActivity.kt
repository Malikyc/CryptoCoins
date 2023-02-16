package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.CoinAdapter

import com.example.myapplication.database.CoinViewModel
import com.example.myapplication.pojo.CoinPriceInfo


class MainActivity : AppCompatActivity() {
   private lateinit var viewModel : CoinViewModel
   private lateinit var recyclerView: RecyclerView
   companion object{
       lateinit var coinAdapter: CoinAdapter
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.RecyclerView)
        var actionBar : ActionBar? = supportActionBar
        if (actionBar!=null){
            actionBar.hide()
        }
        coinAdapter = CoinAdapter()
        coinAdapter.coinOnclickListener = object : CoinAdapter.CoinOnclickListener{
            override fun onClick(coinPriceInfo: CoinPriceInfo) {
               val intent = DetailedCoinInfo.newIntent(this@MainActivity,coinPriceInfo.fromSymbol)
                startActivity(intent)
            }

        }
        recyclerView.adapter = coinAdapter
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        val coinPriceInfo  = viewModel.priceList
        coinPriceInfo.observe(this, Observer {
            coinAdapter.coinInfoList = it
        })


    }

}