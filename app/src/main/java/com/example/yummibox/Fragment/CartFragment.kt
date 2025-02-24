package com.example.yummibox.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummibox.CongratsBottomSheet
import com.example.yummibox.PayOutActivity
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
            R.drawable.herring_under_afur_coat,
            R.drawable.chicken_kiev,
            R.drawable.pancakeswith_red_caviar
        )
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}