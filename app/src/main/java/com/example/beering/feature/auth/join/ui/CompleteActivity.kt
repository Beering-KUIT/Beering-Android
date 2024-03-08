package com.example.beering.feature.auth.join.ui

import androidx.lifecycle.lifecycleScope
import com.example.beering.BeeringApplication
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.repository.UserRepositoryImpl
import com.example.beering.data.onSuccess
import com.example.beering.databinding.ActivityJoinCompleteBinding
import com.example.beering.feature.auth.join.domain.LoginUseCase
import com.example.beering.util.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.userAgent

class CompleteActivity : BaseActivity<ActivityJoinCompleteBinding>(ActivityJoinCompleteBinding::inflate) {
    override fun initAfterBinding() {
        binding.completeLoginIv.setOnClickListener {
            val authService = BeeringApplication.retrofit.create(UserApi::class.java)
            val login = LoginUseCase(UserRepositoryImpl(authService))
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

                    }
            }

        }
    }
}