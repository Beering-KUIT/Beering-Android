package com.example.beering.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent()
        // 상태 바, 네비게이션 높이 만큼 padding 주기
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.root.apply{
            if (this is ViewGroup){
                clipToPadding = false
            }
            setPadding(
                0,
                this@BaseActivity.statusBarHeight(),
                0,
                this@BaseActivity.navigationHeight()
            )
        }
        initAfterBinding()

    }

    protected abstract fun initAfterBinding()

}
