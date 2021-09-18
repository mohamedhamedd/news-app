package com.moapps.newsapp.breakingnews.utils

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.moapps.newsapp.breakingnews.R
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.androidadvance.topsnackbar.TSnackbar


fun LinearLayout.hideLoading() {
    visibility = View.GONE
}

fun LinearLayout.showLoading() {
    visibility = View.VISIBLE
}

fun TextView.showError(txt: String?) {
    visibility = View.VISIBLE
    text = txt
}

fun TextView.hideError() {
    visibility = View.GONE
}

fun ProgressBar.hideBar() {
    visibility = View.GONE
}

fun ProgressBar.showBar() {
    visibility = View.VISIBLE
}

fun showTSnackbar(view: View, txt: String) {
    val snackbar = TSnackbar.make(
        view,
        txt,
        TSnackbar.LENGTH_SHORT
    )
    snackbar.setActionTextColor(Color.WHITE)
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(Color.parseColor("#FF1E1E1E"))
    val textView =
        snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.WHITE)
    snackbar.show()
}