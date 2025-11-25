package com.example.core.common.extension

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Extension functions for View.
 */

/**
 * Sets visibility to VISIBLE.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Sets visibility to GONE.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Sets visibility to INVISIBLE.
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Sets visibility based on condition.
 */
fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

/**
 * Sets a click listener with debounce.
 */
fun View.setOnClickListenerWithDebounce(debounceTime: Long = 600L, action: (View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        
        override fun onClick(v: View) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime) {
                lastClickTime = currentTime
                action(v)
            }
        }
    })
}

/**
 * Shows a short toast message.
 */
fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

/**
 * Shows a long toast message.
 */
fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}
