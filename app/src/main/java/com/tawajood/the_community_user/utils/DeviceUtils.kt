package com.tawajood.the_community_user.utils

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.provider.Settings

@SuppressLint("HardwareIds")
fun Application.getAndroidId(): String {
    return try {
        Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    } catch (e: Exception) {
        e.toString().logE()
        ""
    }
}

fun Application.currentVersionCode(): Long {
    val pInfo = packageManager.getPackageInfo(packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        pInfo.longVersionCode
    } else {
        @Suppress("DEPRECATION")
        pInfo.versionCode.toLong()
    }
}

fun Application.currentVersionName(): String {
    val pInfo = packageManager.getPackageInfo(packageName, 0)
    return pInfo.versionName ?: ""
}
