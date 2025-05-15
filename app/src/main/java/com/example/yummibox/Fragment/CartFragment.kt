package com.example.yummibox.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummibox.PayOutActivity
import com.example.yummibox.adapter.CartAdapter
import com.example.yummibox.databinding.FragmentCartBinding
import com.example.yummibox.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var foodQuantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        reteriveCartItems()

//        val cartFoodName = listOf("Herring Under a Fur Coat", "Chicken Kiev", "Pancakes with Red Caviar")
//        val cartItemPrice = listOf("$5", "$7", "$8")
//        val cartImage = listOf(
//            R.drawable.herring_under_afur_coat,
//            R.drawable.chicken_kiev,
//            R.drawable.pancakeswith_red_caviar
//        )
//        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
//        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.cartRecyclerView.adapter = adapter
        binding.proceedButton.setOnClickListener {
            getOrderItemsDetail()
//            val intent = Intent(requireContext(), PayOutActivity::class.java)
//            startActivity(intent)
        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        // get item Quantities
        val foodQuantities = cartAdapter.getUpdatedItemsQuantites()

        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    // get the cartItems to respectine list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    // add items details in to list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodIngredient?.let { foodIngredient.add(it) }
                }
                orderNow(foodName, foodPrice, foodImage, foodDescription, foodIngredient, foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order making Failed, please try again", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodImage: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context!= null){
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngredient", foodIngredient as ArrayList<String>)
            intent.putExtra("FoodItemQuantities", foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun reteriveCartItems() {
        // database refernce to the Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val foodReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        // list to store cart items
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodImagesUri = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodQuantity = mutableListOf()

        //fetch data from the database
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    // get the cartItems object from the child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
                    //add cart items details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodIngredient?.let {foodIngredients.add(it)}
                    cartItems?.foodQuantity?.let { foodQuantity.add(it) }
                }
                setAdapter()
            }
            private fun setAdapter(){
                cartAdapter = CartAdapter(requireContext(), foodNames, foodPrices, foodImagesUri, foodDescriptions, foodIngredients, foodQuantity)
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {

    }
}