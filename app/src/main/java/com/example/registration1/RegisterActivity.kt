package com.example.registration1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnSave = findViewById(R.id.btnSave)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val age = edtAge.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                saveUserDetails(name, age, email, password)
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()

                // Go back to login page
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()  // Prevent going back to the registration page
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserDetails(name: String, age: String, email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("age", age)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }
}
