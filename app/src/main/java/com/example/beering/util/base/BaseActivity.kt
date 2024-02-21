package com.example.beering.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.marginTop
import androidx.viewbinding.ViewBinding
import com.example.beering.util.navigationHeight
import com.example.beering.util.setStatusBarTransparent
import com.example.beering.util.statusBarHeight

abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent()
//         상태 바, 네비게이션 높이 만큼 padding 주기
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
