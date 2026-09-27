package com.tawajood.the_community_user.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openDialer(context: Context, phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        "openDialer - $e".logE()
    }
}

fun openGmailCompose(context: Context, email: String, subject: String = "", body: String = "") {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        "openGmailCompose - $e".logE()
    }
}

fun openLink(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        "openLink - $e".logE()
    }
}
