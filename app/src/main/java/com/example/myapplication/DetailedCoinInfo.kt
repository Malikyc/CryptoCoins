package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.CoinViewModel
import com.squareup.picasso.Picasso

class DetailedCoinInfo : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    private lateinit var ivLogoCoin : ImageView
    private lateinit var tvPrice : TextView
    private lateinit var tvMinPrice : TextView
    private lateinit var tvMaxPrice : TextView
    private lateinit var tvLastMarket : TextView
    private lateinit var tvLastUpdate : TextView
    private lateinit var tvFromSymbol : TextView
    private lateinit var tvToSymbol : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_coin_info)
        tvToSymbol = findViewById(R.id.tvToSymbol)
        tvFromSymbol = findViewById(R.id.tvFromSymbol)
        tvPrice = findViewById(R.id.tvPrice)
        tvMinPrice = findViewById(R.id.tvMinPrice)
        tvMaxPrice = findViewById(R.id.tvMaxPrice)
        tvLastMarket = findViewById(R.id.tvLastMarket)
        tvLastUpdate =findViewById(R.id.tvLastUpdate)
        ivLogoCoin = findViewById(R.id.ivLogoCoin)

        if (!intent.hasExtra(EXTRA_FSYM_KEY)){
            finish()
            return
        }
        val extraCoin = intent.getStringExtra(EXTRA_FSYM_KEY)
        if(extraCoin != null){
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
            val coin =  viewModel.getDetailedCoinInfo(extraCoin)
            coin.observe(this) {
             tvToSymbol.text = it.toSymbol
             tvFromSymbol.text = it.fromSymbol
             tvPrice.text = it.price
             tvMinPrice.text = it.lowDay
             tvMaxPrice.text = it.highDay
             tvLastMarket.text = it.lastMarket
             tvLastUpdate.text = it.getFormatedLastUpdate()
             Picasso.get().load(it.getFullImgUrl()).into(ivLogoCoin)
         }}

    }
    companion object{
        private const val EXTRA_FSYM_KEY = "fSym"

        fun newIntent(context : Context,fromSymbol:String?) : Intent {
            val intent = Intent(context,DetailedCoinInfo::class.java)
            intent.putExtra(EXTRA_FSYM_KEY,fromSymbol)
            return intent
        }
    }

}