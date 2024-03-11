package com.example.beering.feature.auth.join.ui

import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.beering.BeeringApplication
import com.example.beering.R
import com.example.beering.data.auth.api.TokenSpf
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.repository.UserRepositoryImpl
import com.example.beering.data.onFail
import com.example.beering.data.onSuccess
import com.example.beering.databinding.ActivityJoinCompleteBinding
import com.example.beering.feature.MainActivity
import com.example.beering.feature.auth.login.domain.LoginUseCase
import com.example.beering.feature.auth.login.ui.LoginActivity
import com.example.beering.util.base.BaseActivity
import com.example.beering.util.changeLogin
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CompleteActivity : BaseActivity<ActivityJoinCompleteBinding>(ActivityJoinCompleteBinding::inflate) {
    override fun initAfterBinding() {
        val name = intent.getStringExtra("name")!!
        binding.completeTitleTv.text = getString(R.string.complete_title, name)
        binding.completeLoginIv.setOnClickListener {
            val authService = BeeringApplication.retrofit.create(UserApi::class.java)
            val login = LoginUseCase(UserRepositoryImpl(authService, TokenSpf()))
            val id = intent.getStringExtra("id")!!
            val pw = intent.getStringExtra("pw")!!
            lifecycleScope.launch {
                // 코루틴을 실행합니다.
                val result = withContext(Dispatchers.IO) {
                    login(id, pw)
                }
                // UI를 업데이트합니다.
                result
                    .onSuccess {
                        login.saveTokens(it.jwtInfo.accessToken, it.jwtInfo.refreshToken)
                        changeLogin(true)
                        val mIntent = Intent(this@CompleteActivity, MainActivity::class.java)
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(mIntent)
                    }
                    .onFail { code, msg ->
                        Snackbar.make(binding.root, msg + "errorCode : $code", Snackbar.LENGTH_SHORT)
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    super.onDismissed(transientBottomBar, event)
                                    val mIntent = Intent(this@CompleteActivity, LoginActivity::class.java)
                                    mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(mIntent)
                                }
                            }).show()
                    }
            }

        }
    }
}