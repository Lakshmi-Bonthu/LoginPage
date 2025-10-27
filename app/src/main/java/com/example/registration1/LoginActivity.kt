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


class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Countly.sharedInstance().views().recordView("LoginActivity")

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "LoginActivity")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginActivity")
        })

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        edtEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                firebaseAnalytics.logEvent("clicked_email_inLogin", null)
                Countly.sharedInstance().events().recordEvent("clicked_email_inLogin", null, 1)
            }
        }

        edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                firebaseAnalytics.logEvent("clicked_password_inLogin", null)
                Countly.sharedInstance().events().recordEvent("clicked_password_inLogin", null, 1)
            }
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            firebaseAnalytics.logEvent("login_button_clicked", Bundle().apply {
                putString("button_name", "Login")
                putString("screen", "LoginActivity")
            })

            Countly.sharedInstance().events().recordEvent("login_button_clicked", mapOf("screen" to "LoginActivity"), 1)

            if (validateLogin(email, password)) {
                firebaseAnalytics.setUserId(email)
                firebaseAnalytics.logEvent("user_login_attempt", Bundle().apply {
                    putString("email", email)
                    putString("login_status", "success")
                })
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle().apply {
                    putString(FirebaseAnalytics.Param.METHOD, "email")
                })

                val userSegmentation = mapOf(
                    "email" to email,
                    "login_status" to "success"
                )
                Countly.sharedInstance().events().recordEvent("user_info_logged", userSegmentation, 1)


                Countly.sharedInstance().events().recordEvent("user_login_attempt", mapOf(
                    "email" to email,
                    "login_status" to "success"
                ), 1)

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            firebaseAnalytics.logEvent("register_button_clicked", Bundle().apply {
                putString("button_name", "Register")
                putString("screen", "LoginActivity")
            })
            Countly.sharedInstance().events().recordEvent("register_button_clicked", mapOf("screen" to "LoginActivity"), 1)

            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")
        return email == savedEmail && password == savedPassword
    }
}
