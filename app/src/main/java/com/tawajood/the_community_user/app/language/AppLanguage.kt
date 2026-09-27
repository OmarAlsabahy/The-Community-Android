package com.tawajood.the_community_user.app.language

enum class AppLanguage(val code: String) {
    ARABIC("ar"),
    ENGLISH("en");

    companion object {
        fun fromCode(code: String?): AppLanguage =
            if (code?.startsWith("ar", ignoreCase = true) == true) ARABIC else ENGLISH
    }
}
