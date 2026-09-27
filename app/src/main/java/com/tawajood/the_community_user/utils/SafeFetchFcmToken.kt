package com.tawajood.the_community_user.utils

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.io.IOException

object SafeFetchFcmToken {

    suspend fun safeFetchFcmToken(
        maxRetries: Int = 5,
        baseDelayMs: Long = 1000L
    ): String? {
        var attempt = 0
        var delayMs = baseDelayMs
        while (attempt < maxRetries) {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                if (!token.isNullOrBlank()) return token
            } catch (e: IOException) {
                e.logE()
            } catch (e: Exception) {
                e.logE()
            }
            attempt++
            delay(delayMs)
            delayMs *= 2
        }
        return null
    }
}