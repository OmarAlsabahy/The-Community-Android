package com.tawajood.the_community_user.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import com.tawajood.the_community_user.R

class ToastUtils {
    companion object {
        private var toastMessage: Toast? = null

        // Toast types
        const val SUCCESS = 1
        const val ERROR = 2
        const val INFO = 3
        const val WARNING = 4
        const val DEFAULT = 0

        /**
         * Show a custom styled toast message
         * @param context The application context
         * @param message The message to display
         * @param toastType The type of toast (SUCCESS, ERROR, INFO, WARNING, DEFAULT)
         * @param duration Duration of toast (Toast.LENGTH_SHORT or Toast.LENGTH_LONG)
         */
        @SuppressLint("ShowToast", "InflateParams", "MissingInflatedId")
        fun showCustomToast(
            context: Context,
            message: String,
            toastType: Int = DEFAULT,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            if (message == "null") return

            // Cancel previous toast if showing
            toastMessage?.cancel()

            // Inflate custom layout
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.custom_toast_layout, null)

            // Get views
            val toastCard = layout.findViewById<CardView>(R.id.toast_card)
            val textView = layout.findViewById<TextView>(R.id.toast_text)
            val iconView = layout.findViewById<ImageView>(R.id.toast_icon)

            // Set text
            textView.text = message

            // Configure style based on type
            when (toastType) {
                SUCCESS -> {
                    setToastStyle(
                        toastCard,
                        textView,
                        iconView,
                        R.drawable.ic_success,
                        "#4CAF50".toColorInt(),
                        Color.WHITE
                    )
                }

                ERROR -> {
                    setToastStyle(
                        toastCard,
                        textView,
                        iconView,
                        R.drawable.ic_error,
                        "#F44336".toColorInt(),
                        Color.WHITE
                    )
                }

                INFO -> {
                    setToastStyle(
                        toastCard,
                        textView,
                        iconView,
                        R.drawable.ic_info,
                        "#2196F3".toColorInt(),
                        Color.WHITE
                    )
                }

                WARNING -> {
                    setToastStyle(
                        toastCard,
                        textView,
                        iconView,
                        R.drawable.ic_warning,
                        "#FFC107".toColorInt(),
                        Color.BLACK
                    )
                }

                else -> {
                    setToastStyle(
                        toastCard,
                        textView,
                        iconView,
                        null,
                        "#333333".toColorInt(),
                        Color.WHITE
                    )
                    iconView.visibility = View.GONE
                }
            }

            // Create and show toast
            toastMessage = Toast(context)
            toastMessage?.apply {
                setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 64)
                this.duration = duration
                @Suppress("DEPRECATION")
                view = layout
                show()
            }
        }

        /**
         * Configure the visual style of the toast
         */
        private fun setToastStyle(
            cardView: CardView,
            textView: TextView,
            iconView: ImageView,
            iconRes: Int?,
            backgroundColor: Int,
            textColor: Int
        ) {
            // Set background color
            cardView.setCardBackgroundColor(backgroundColor)

            // Set text color
            textView.setTextColor(textColor)

            // Set icon if provided
            if (iconRes != null) {
                iconView.visibility = View.VISIBLE
                iconView.setImageResource(iconRes)
                // Apply tint to match text color
                iconView.setColorFilter(textColor)
            } else {
                iconView.visibility = View.GONE
            }
        }

        /**
         * Helper methods for easier usage
         */
        fun showSuccessToast(context: Context, message: String) {
            showCustomToast(context, message, SUCCESS)
        }

        fun showErrorToast(context: Context, message: String) {
            showCustomToast(context, message, ERROR)
        }

        fun showInfoToast(context: Context, message: String) {
            showCustomToast(context, message, INFO)
        }

        fun showWarningToast(context: Context, message: String) {
            showCustomToast(context, message, WARNING)
        }
    }
}