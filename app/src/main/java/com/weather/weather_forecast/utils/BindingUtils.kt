package com.weather.weather_forecast.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

class BindingUtils {

    companion object {

        @BindingAdapter("android:visibility")
        fun setVisibility(view: View, show: Boolean) {
            view.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }
}