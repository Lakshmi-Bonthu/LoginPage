package com.example.registration1

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import ly.count.android.sdk.Countly

class HomeActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var tvLoginSuccess: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserAge: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvUserPassword: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Countly.sharedInstance().views().recordView("HomeActivity")

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "HomeActivity")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeActivity")
        })

        tvLoginSuccess = findViewById(R.id.tvLoginSuccess)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserAge = findViewById(R.id.tvUserAge)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        tvUserPassword = findViewById(R.id.tvUserPassword)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val name = sharedPreferences.getString("name", "N/A")
        val age = sharedPreferences.getString("age", "N/A")
        val email = sharedPreferences.getString("email", "N/A")

        tvLoginSuccess.text = "Login Successful!"
        tvUserName.text = "Name: $name"
        tvUserAge.text = "Age: $age"
        tvUserEmail.text = "Email: $email"
        tvUserPassword.text = "Password: ********"

        firebaseAnalytics.logEvent("user_details_displayed", Bundle().apply {
            putString("name", name)
            putString("age", age)
            putString("email", email)
        })

        Countly.sharedInstance().events().recordEvent("user_details_displayed", mapOf(
            "name" to name,
            "age" to age,
            "email" to email
        ), 1)

        val clickLogger: (TextView, String) -> Unit = { view, id ->
            view.setOnClickListener {
                firebaseAnalytics.logEvent("textview_clicked", Bundle().apply {
                    putString("textview_id", id)
                    putString("screen", "HomeActivity")
                })
                Countly.sharedInstance().events().recordEvent("textview_clicked", mapOf(
                    "textview_id" to id,
                    "screen" to "HomeActivity"
                ), 1)
            }
        }

        clickLogger(tvLoginSuccess, "tvLoginSuccess")
        clickLogger(tvUserName, "tvUserName")
        clickLogger(tvUserAge, "tvUserAge")
        clickLogger(tvUserEmail, "tvUserEmail")
        clickLogger(tvUserPassword, "tvUserPassword")
    }
}
