package com.example.registration1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import ly.count.android.sdk.Countly

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Countly.sharedInstance().views().recordView("RegisterActivity")

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "RegisterActivity")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "RegisterActivity")
        })

        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnSave = findViewById(R.id.btnSave)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val logFocus = { view: EditText, eventName: String ->
            view.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    firebaseAnalytics.logEvent(eventName, null)
                    Countly.sharedInstance().events().recordEvent(eventName, null, 1)
                }
            }
        }

        logFocus(edtName, "clicked_name")
        logFocus(edtAge, "clicked_age")
        logFocus(edtEmail, "clicked_email")
        logFocus(edtPassword, "clicked_password")

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val age = edtAge.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            firebaseAnalytics.logEvent("save_button_clicked", Bundle().apply {
                putString("button_name", "Save")
                putString("screen", "RegisterActivity")
            })

            Countly.sharedInstance().events().recordEvent("save_button_clicked", mapOf("screen" to "RegisterActivity"), 1)

            if (name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAnalytics.setUserId(email)
                firebaseAnalytics.setUserProperty("user_name", name)
                firebaseAnalytics.setUserProperty("user_age", age)
                firebaseAnalytics.logEvent("user_registered", Bundle().apply {
                    putString("name", name)
                    putString("age", age)
                    putString("email", email)
                })
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, Bundle().apply {
                    putString(FirebaseAnalytics.Param.METHOD, "email")
                })

                Countly.sharedInstance().events().recordEvent("user_registered", mapOf(
                    "name" to name,
                    "age" to age,
                    "email" to email
                ), 1)

                saveUserDetails(name, age, email, password)
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
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
