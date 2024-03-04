package com.example.beering.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.viewbinding.ViewBinding
import com.example.beering.util.navigationHeight
import com.example.beering.util.setStatusBarTransparent
import com.example.beering.util.statusBarHeight

abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent()
//         상태 바, 네비게이션 높이 만큼 padding 주기
//        binding.root.apply{
//            if (this is ViewGroup){
//                clipToPadding = false
//            }
//            setPadding(
//                0,
//                0,
//                0,
//                this@BaseActivity.navigationHeight()
//            )
//        }

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        initAfterBinding()

    }
    //edittext 이외의 화면 클릭시 키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                hideKeyboard(view)
        }
        return super.dispatchTouchEvent(ev)
    }

    protected abstract fun initAfterBinding()

    fun hideKeyboard(v: View) {
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}






