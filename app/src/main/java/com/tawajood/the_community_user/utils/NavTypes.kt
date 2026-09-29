package com.tawajood.the_community_user.utils

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T: Any> serializableNavType(
    isNullableAllowed: Boolean = false,
    json: Json = Json { ignoreUnknownKeys = true; isLenient = true },
): NavType<T> = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.putString(key, json.encodeToString<T>(value))
    }

    override fun get(bundle: SavedState, key: String): T? {
        return bundle.getString(key)?.let { json.decodeFromString<T>(it) }
    }

    override fun parseValue(value: String): T {
        return json.decodeFromString<T>(Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(json.encodeToString<T>(value))
    }
}