package com.example.yummibox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.yummibox.databinding.ActivitySignBinding
import com.example.yummibox.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class SignActivity : AppCompatActivity() {
    
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding : ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

//        initialize Firebase auth
        auth = Firebase.auth
//        initialize Firebase auth
        database = Firebase.database.reference

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.createAccountButton.setOnClickListener {
            username = binding.userName.text.toString()
            email = binding.emailAddress.text.toString()
            password = binding.password.text.toString()
            
            if (email.isBlank() || password.isBlank() || username.isBlank()){
                Toast.makeText(this, "Please Fill all the details 🙄", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email, password)
            }
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        binding.AlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.googleButton.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
    }
//    launcher go to google sign-in
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign In Succeed 🤗", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign In Field 😥", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else{
                Toast.makeText(this, "Sign In Field 😥", Toast.LENGTH_SHORT).show()
            }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    private fun saveUserData() {
//        retrieve data from input filed
        username = binding.userName.text.toString()
        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//        save data to Firebase data base
        database.child("user").child(userId).setValue(user)
    }
}