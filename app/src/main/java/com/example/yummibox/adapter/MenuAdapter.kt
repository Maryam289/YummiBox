package com.example.yummibox.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummibox.DetailsActivity
import com.example.yummibox.databinding.MenuItemBinding
import com.example.yummibox.model.CartItems
import com.example.yummibox.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext:Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


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
                    openDetailsActivity(position)
                }
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
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)

                menuAddToCartButton.setOnClickListener {
                    addItemToCart(menuItem)
                }
            }
        }

        private fun addItemToCart(menuItem: MenuItem) {
            val auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser?.uid ?: return
            val database = FirebaseDatabase.getInstance().reference

            val cartItem = CartItems(
                foodName = menuItem.foodName ?: "",
                foodPrice = menuItem.foodPrice ?: "",
                foodImage = menuItem.foodImage ?: "",
                foodDescription = menuItem.foodDescription ?: "",
                foodIngredient = menuItem.foodIngredient ?: "",
                foodQuantity = 1
            )

            database.child("user").child(userId).child("CartItems").push().setValue(cartItem)
                .addOnSuccessListener {
                    Toast.makeText(requireContext, "Item added to cart successfully 😃", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext, "Failed to add item 😢", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


