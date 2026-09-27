package com.tawajood.the_community_user.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.vanniktech.locale.Country

fun isPhoneValidForCountry(
    rawPhone: String,
    country: Country
): Boolean {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return try {
        val numberProto = phoneUtil.parse(
            rawPhone,
            country.code,
        )
        phoneUtil.isValidNumberForRegion(numberProto, country.code)
    } catch (e: Exception) {
        "isPhoneValidForCountry - error - $e".logE()
        false
    }
}

fun Country.Companion.findByCallingCode(code: String): Country? {
    return Country.values().firstOrNull { it.callingCodes.contains(code) }
}
