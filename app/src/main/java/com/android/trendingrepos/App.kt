package com.android.trendingrepos

import android.app.Application
import android.content.Intent
import com.google.android.gms.security.ProviderInstaller

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ProviderInstaller.installIfNeededAsync(this, object : ProviderInstaller.ProviderInstallListener {
            override fun onProviderInstalled() {}

            override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent) {}
        })
    }
}