package com.example.registration1

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var tvLoginSuccess: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserAge: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvUserPassword: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvLoginSuccess = findViewById(R.id.tvLoginSuccess)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserAge = findViewById(R.id.tvUserAge)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        tvUserPassword = findViewById(R.id.tvUserPassword)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Display Login Successful message
        tvLoginSuccess.text = "Login Successful!"

        // Retrieve user details from SharedPreferences
        val name = sharedPreferences.getString("name", "N/A")
        val age = sharedPreferences.getString("age", "N/A")
        val email = sharedPreferences.getString("email", "N/A")
        val password = sharedPreferences.getString("password", "N/A")

        // Set text for user details
        tvUserName.text = "Name: $name"
        tvUserAge.text = "Age: $age"
        tvUserEmail.text = "Email: $email"
        tvUserPassword.text = "Password: $password"
    }
}
