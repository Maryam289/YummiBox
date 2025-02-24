package com.example.yummibox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummibox.adapter.MenuAdapter
import com.example.yummibox.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        val menuFoodName = listOf("Herring Under a Fur Coat", "Chicken Kiev", "Pancakes with Red Caviar")
        val menuItemPrice = listOf("$5", "$7", "$8")
        val menuImage = listOf(
            R.drawable.herring_under_afur_coat,
            R.drawable.chicken_kiev,
            R.drawable.pancakeswith_red_caviar
        )
        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuItemPrice),
            ArrayList(menuImage),
            requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}