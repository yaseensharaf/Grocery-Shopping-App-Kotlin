package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val nameEditText = findViewById<EditText>(R.id.etName)
        val emailEditText = findViewById<EditText>(R.id.etEmail)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val phoneEditText = findViewById<EditText>(R.id.etPhoneNumber)
        val addressEditText = findViewById<EditText>(R.id.etAddress)
        val signUpButton = findViewById<Button>(R.id.btnSignUp)
        progressBar = findViewById(R.id.signUpProgressBar)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()

            if (validateInputs(name, email, password, phone, address)) {
                signUpUser(name, email, password, phone, address)
            }
        }
    }

    private fun validateInputs(name: String, email: String, password: String, phone: String, address: String): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.length < 10) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun signUpUser(name: String, email: String, password: String, phone: String, address: String) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    saveUserToDatabase(userId, name, email, phone, address)
                } else {
                    Toast.makeText(
                        this,
                        "Sign Up Failed: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun saveUserToDatabase(userId: String, name: String, email: String, phone: String, address: String) {
        val user = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "address" to address
        )
        database.reference.child("users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomePage::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to Save User Details: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
