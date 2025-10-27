package com.example.registration1

import android.app.Application
import ly.count.android.sdk.Countly
import ly.count.android.sdk.CountlyConfig

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = CountlyConfig(this, "8ea6a23c7f3d5ed8f5f895930f1b5bc6fe1e1073", "https://abc-ac7b5e769716b.flex.countly.com")
        config.setLoggingEnabled(true)

        Countly.sharedInstance().init(config)
    }
}
