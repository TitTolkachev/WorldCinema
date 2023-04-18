package com.example.worldcinema.ui.dialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.worldcinema.R

fun AppCompatActivity.showAlertDialog(alert: AlertType) {

    val dialog = AlertDialog()
    val bundle = Bundle()
    bundle.putInt(
        getString(R.string.alert_dialog_data_text),
        alert.ordinal
    )
    dialog.arguments = bundle
    dialog.show(supportFragmentManager, "alert_dialog")
}

fun Fragment.showAlertDialog(alert: AlertType) {

    val dialog = AlertDialog()
    val bundle = Bundle()
    bundle.putInt(
        getString(R.string.alert_dialog_data_text),
        alert.ordinal
    )
    dialog.arguments = bundle
    dialog.show(childFragmentManager, "alert_dialog")
}