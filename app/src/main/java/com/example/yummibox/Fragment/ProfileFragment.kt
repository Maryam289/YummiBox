package com.example.yummibox.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.yummibox.databinding.FragmentProfileBinding
import com.example.yummibox.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUserData()

        binding.apply {
        name.isEnabled = false
        address.isEnabled = false
        email.isEnabled = false
        phone.isEnabled = false
        binding.editButton.setOnClickListener {

            name.isEnabled = !name.isEnabled
            name.setTextColor(Color.BLACK)
            address.isEnabled = !address.isEnabled
            address.setTextColor(Color.BLACK)
            email.isEnabled = !email.isEnabled
            email.setTextColor(Color.BLACK)
            phone.isEnabled = !phone.isEnabled
            phone.setTextColor(Color.BLACK)

            }
        }
        binding.saveInformationButton.setOnClickListener {
            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val email = binding.email.text.toString()
            val phone = binding.phone.text.toString()

            updateUserData(name, address, email, phone)
        }

        // Inflate the layout for this fragment
        return binding.root

    }

    private fun updateUserData(name: String, address: String, email: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Update Successfully 🥳", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile Update Failed 😥", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null){
                            binding.name.setText(userProfile.name)
                            binding.address.setText(userProfile.address)
                            binding.email.setText(userProfile.email)
                            binding.phone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

}