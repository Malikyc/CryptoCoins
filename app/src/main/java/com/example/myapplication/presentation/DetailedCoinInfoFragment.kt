package com.example.myapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentDetailedCoinInfoBinding
import com.squareup.picasso.Picasso

class DetailedCoinInfoFragment : Fragment() {

    private val fsym : String by lazy {
        requireArguments().getString(EXTRA_FSYM_KEY).toString()
            ?: throw RuntimeException("No fromSymbol passed")
    }

    private val viewModel : CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }
    private var _binding : FragmentDetailedCoinInfoBinding? = null
    private val binding : FragmentDetailedCoinInfoBinding
    get(){
        return _binding ?: throw RuntimeException("FragmentDetailedCoinInfoBinding is null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedCoinInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewSetting()
    }


    private fun viewSetting() {
            viewModel.getCoinByFsym(fsym)
        viewModel.particularCoinInfo.observe(viewLifecycleOwner) {
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

        fun newInstance(fromSymbol:String?) : DetailedCoinInfoFragment {
            return DetailedCoinInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FSYM_KEY,fromSymbol)
                }
            }
        }
    }

}