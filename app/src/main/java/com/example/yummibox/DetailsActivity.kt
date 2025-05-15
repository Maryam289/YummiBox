package com.example.yummibox

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.yummibox.databinding.ActivityDetailsBinding
import com.example.yummibox.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodImage = intent.getStringExtra("MenuItemImage")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")

        with(binding){
            detailFoodName.text = foodName
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(DetailFoodImage)
            DescriptionTextView.text = foodDescription
            IngredientsTextView.text = foodIngredients
        }

        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.addItemButton.setOnClickListener {
            addItemToCart()
        }


    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""
        // create a cartItems object
        val cartItem = CartItems(foodName.toString(), foodPrice.toString(), foodImage.toString(), foodDescription.toString(), foodIngredients.toString(), 1)

        // save data to cart item to firebase database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Items added into cart successfully 😃", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item not added 😢", Toast.LENGTH_SHORT).show()
        }


    }
}