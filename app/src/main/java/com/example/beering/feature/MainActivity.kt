package com.example.beering.feature

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.beering.feature.search.DrinkSearchFragment
import com.example.beering.feature.home.HomeFragment
import com.example.beering.feature.my.MyFragment
import com.example.beering.R
import com.example.beering.databinding.ActivityMainBinding
import com.example.beering.feature.archive.ui.ArchiveFragment
import com.example.beering.util.base.BaseActivity
import com.example.beering.util.ignoreRootPadding
import com.kakao.sdk.common.util.Utility

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    var exit_millis : Long = 0
    override fun initAfterBinding() {
        installSplashScreen()
        initBottomNavigation()

        Log.d("test", "keyhash : ${Utility.getKeyHash(this)}")

    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.selectedItemId = R.id.menu_home


        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, DrinkSearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_my -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_archive -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ArchiveFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

        binding.mainBnv.itemIconTintList = null


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)
                if (fragment is HomeFragment) {
                    killApp()
                } else {
                    binding.mainBnv.selectedItemId = R.id.menu_home
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }


    fun killApp() {
        if (System.currentTimeMillis() - exit_millis > 2000) {
            exit_millis = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }


    }

}