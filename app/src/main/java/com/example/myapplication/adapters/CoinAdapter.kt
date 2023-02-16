package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.pojo.CoinPriceInfo
import com.example.myapplication.R
import com.squareup.picasso.Picasso


class CoinAdapter() : RecyclerView.Adapter<CoinAdapter.CoinViewHolder> () {

    interface CoinOnclickListener{
        fun onClick(coinPriceInfo: CoinPriceInfo)

    }

     var coinOnclickListener : CoinOnclickListener? = null
    set(value){
        field = value
    }

    var coinInfoList: List<CoinPriceInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CoinViewHolder(itemView: View) : ViewHolder(itemView) {
        val coinName: TextView = itemView.findViewById(R.id.coinName)
        val coinPrice: TextView = itemView.findViewById(R.id.coinPrice)
        val lastUpdate: TextView = itemView.findViewById(R.id.lastUpdate)
        val coinImage: ImageView = itemView.findViewById(R.id.coinImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinPriceInfo: CoinPriceInfo = coinInfoList[position]
        holder.coinName.text = coinPriceInfo.fromSymbol + "/"+coinPriceInfo.toSymbol
        holder.lastUpdate.text =
            "Последнее обновление было:${coinPriceInfo.getFormatedLastUpdate()}"
        Picasso.get().load(coinPriceInfo.getFullImgUrl()).into(holder.coinImage)
        holder.coinPrice.text = coinPriceInfo.price
        holder.itemView.setOnClickListener {
                coinOnclickListener?.onClick(coinPriceInfo)
        }
    }

    override fun getItemCount(): Int {
        return coinInfoList.size
    }



    }

