package com.example.myapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.CoinInfoBinding
import com.example.myapplication.domain.CoinInfo
import com.example.myapplication.presentation.CoinDiffUtil
import com.example.myapplication.presentation.CoinViewHolder
import com.squareup.picasso.Picasso


class CoinAdapter : RecyclerView.Adapter<CoinViewHolder> () {

     var coinOnclickListener : ((coinInfo : CoinInfo) -> Unit)? = null


    var coinInfoList: List<CoinInfo> = mutableListOf()
        set(value) {
            val callBack = CoinDiffUtil(coinInfoList,value)
            val difResult = DiffUtil.calculateDiff(callBack)
            difResult.dispatchUpdatesTo(this)
            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = CoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinInfo: CoinInfo = coinInfoList[position]
        holder.binding.coinName.text = coinInfo.fromSymbol + "/"+coinInfo.toSymbol
        holder.binding.lastUpdate.text =
            "Последнее обновление было:${coinInfo.lastUpdate}"
        Picasso.get().load(coinInfo.imageUrl).into(holder.binding.coinImage)
        holder.binding.coinPrice.text = coinInfo.price
        holder.itemView.setOnClickListener {
                coinOnclickListener?.invoke(coinInfo)
        }
    }

    override fun getItemCount(): Int {
        return coinInfoList.size
    }



    }

