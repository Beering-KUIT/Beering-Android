package com.example.beering.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.navigationHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Activity.setStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.let {
            it.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
//    window.apply {
////        setFlags(
////            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
////            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
////        )
//        this.statusBarColor = Color.TRANSPARENT
//        decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//    }
//    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//    }
}

fun dpToPx(dp: Int, context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density).toInt()
}

// Statusbar padding 무시하고 최상단에 딱 붙여야 하는 경우
fun View.ignoreRootPadding(context : Context){
    val param = layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(marginLeft, -context.statusBarHeight(), marginRight, marginBottom)
    layoutParams = param
}

fun View.addStatusBarMarginTop(context : Context){
    val param = layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(marginLeft,marginTop + context.statusBarHeight(), marginRight, marginBottom)
    layoutParams = param
}
