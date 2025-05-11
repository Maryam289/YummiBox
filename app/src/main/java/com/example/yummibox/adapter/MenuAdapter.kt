package com.example.yummibox.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummibox.DetailsActivity
import com.example.yummibox.databinding.MenuItemBinding
import com.example.yummibox.model.MenuItem

class MenuAdapter(
//    private val menuItemsName:MutableList<String>,
//    private val menuItemPrice:MutableList<String>,
//    private val MenuImage:MutableList<Int>,
    private val menuItems: List<MenuItem>,
    private val requireContext:Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

//    private val itemClickListener: OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
//                    itemClickListener?.onItemClick(position)
                    openDetailsActivity(position)
                }

                // setonclick listner to open details
//                val intent = Intent(requireContext, DetailsActivity::class.java)
//                intent.putExtra("MenuItemName", menuItemsName.get(position))
//                intent.putExtra("MenuItemImage", MenuImage.get(position))
//                requireContext.startActivity(intent)
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]

            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemPrice", menuItem.foodPrice)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemIngredients", menuItem.foodIngredient)
            }
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
//                menuImage.setImageResource(MenuImage[position])
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)

            }
        }

    }
//    interface OnClickListener{
//        fun onItemClick(position: Int)
//    }
}


