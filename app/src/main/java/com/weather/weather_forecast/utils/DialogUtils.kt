package com.weather.weather_forecast.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogUtils {
    companion object {

        private var builder: AlertDialog.Builder? = null
        private var dialog: AlertDialog? = null

        private var dialogValid = builder != null && dialog != null

        fun showMessageDialog(context: Context, title: String, message: String = "",
            positiveBtnText: String = "", negativeBtnText: String = "",
            listener: DialogListener) {

            builder = if (dialogValid) {
                if (dialog?.isShowing!!){
                    return
                }
                AlertDialog.Builder(context)
            } else {
                AlertDialog.Builder(context)
            }

            dialog = with(builder)
            {
                this?.setCancelable(false)
                this?.setTitle(title)
                if (message.isNotEmpty()) {
                    this?.setMessage(message)
                }
                if (positiveBtnText.isNotEmpty()) {
                    this?.setPositiveButton(positiveBtnText) { dialog, _ ->
                        dialog.dismiss()
                        listener.onPositiveButtonClick()
                    }
                }
                if (negativeBtnText.isNotEmpty()) {
                    this?.setNegativeButton(negativeBtnText) { dialog, _ ->
                        dialog.dismiss()
                        listener.onPositiveButtonClick()
                    }
                }
                this?.show()
            }
        }
    }
}