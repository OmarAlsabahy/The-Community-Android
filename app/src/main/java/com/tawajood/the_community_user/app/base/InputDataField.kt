package com.tawajood.the_community_user.app.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

interface InputDataField {
    fun isValid(): Boolean
}

@Parcelize
data class InputField<T>(
    val value: @RawValue T,
    val isError: Boolean = false,
    val isEdited: Boolean = false,
    val validation: (T) -> Boolean = { true }
) : InputDataField, Parcelable {
    override fun isValid(): Boolean {
        return validation(value)
    }
}