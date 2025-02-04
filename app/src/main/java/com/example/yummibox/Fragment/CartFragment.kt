package com.example.yummibox.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummibox.R
import com.example.yummibox.adapter.CartAdapter
import com.example.yummibox.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)


        val cartFoodName = listOf("Herring Under a Fur Coat", "Chicken Kiev", "Pancakes with Red Caviar")
        val cartItemPrice = listOf("$5", "$7", "$8")
        val cartImage = listOf(
            R.drawable.HerringUnderAFurCoat,
            R.drawable.ChickenKiev,
            R.drawable.PancakeswithRedCaviar
        )
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}