package com.tawajood.the_community_user.utils

import android.util.Log

fun Any?.logD(tag: String = "BelTAGD") {
    Log.d(tag, this.toString())
}

fun Any?.logW(tag: String = "BelTAGW") {
    Log.w(tag, this.toString())
}

fun Any?.logE(tag: String = "BelTAGE") {
    Log.e(tag, this.toString())
}