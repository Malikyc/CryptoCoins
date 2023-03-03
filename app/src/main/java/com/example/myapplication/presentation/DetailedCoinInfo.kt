package com.example.myapplication.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import com.example.myapplication.data.network.CoinInfoDto
import com.example.myapplication.databinding.ActivityDetailedCoinInfoBinding
import com.example.myapplication.databinding.CoinInfoBinding

class DetailedCoinInfo : AppCompatActivity() {

    private val viewModel : CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }
    private val binding : ActivityDetailedCoinInfoBinding by lazy {
        ActivityDetailedCoinInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(!intent.hasExtra(EXTRA_FSYM_KEY)){
            finish()
        }
        viewSetting()
    }

    private fun viewSetting() {
        val fsym = intent.getStringExtra(EXTRA_FSYM_KEY)?.let {
            viewModel.getCoinByFsym(it)
        }
        viewModel.particularCoinInfo.observe(this) {
            with(binding) {
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }

    companion object{
        private const val EXTRA_FSYM_KEY = "fSym"

        fun newIntent(context : Context,fromSymbol:String?) : Intent {
            val intent = Intent(context, DetailedCoinInfo::class.java)
            intent.putExtra(EXTRA_FSYM_KEY,fromSymbol)
            return intent
        }
    }

}