package com.example.beering.feature.auth.join.ui

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.beering.R
import com.example.beering.databinding.ActivityJoinBinding
import com.example.beering.util.base.BaseActivity
import com.google.android.material.snackbar.Snackbar

class JoinActivity: BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate) {
    private val joinViewModel : JoinViewModel by viewModels { JoinViewModel.Factory }

    override fun initAfterBinding() {
        binding.joinHeaderCl.toolbarBackIv.setOnClickListener {
            finish()
        }

        // 객체 생성
        val userIdEdit = binding.joinIdEd
        val passwordEdit = binding.joinPasswordEd
        val passwordAgainEdit = binding.joinPasswordAgainEd
        val nicknameEdit = binding.joinNicknameEd

        // ViewModel - Observer 초기화
        joinViewModel.userId.observe(this, Observer { userId ->
            // 이곳에서 userId가 변경될 때마다 실행되는 코드 작성
            if(userId.isNotEmpty()){
                userIdEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinIdBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinIdIv1.setImageResource(R.drawable.ic_delete_dark)
                binding.joinIdIv21.visibility = View.INVISIBLE
                binding.joinIdIv22.visibility = View.VISIBLE

                binding.joinIdIv1.setOnClickListener {
                    binding.joinIdEd.text.clear()
                    joinViewModel.setUserId("")
                }
            } else {
                userIdEdit.setTextColor(ContextCompat.getColor(this@JoinActivity,
                    R.color.gray01
                ))
                binding.joinIdBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinIdIv1.setImageResource(R.drawable.ic_delete_light)
                binding.joinIdIv21.visibility = View.VISIBLE
                binding.joinIdIv22.visibility = View.GONE
            }
        })
        joinViewModel.password.observe(this, Observer {
            if (it.isNotEmpty()) {
                passwordEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinPasswordBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_red
                    )
                )
                binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_dark)
                binding.joinPasswordInvisibleIv.visibility = View.VISIBLE

            } else {
                passwordEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinPasswordBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_light)
                binding.joinPasswordVisibleIv.visibility = View.GONE
                binding.joinPasswordInvisibleIv.visibility = View.GONE
            }
        })
        joinViewModel.passwordAgain.observe(this, Observer{
            if (it.isNotEmpty()) {
                passwordAgainEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_dark)
                binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE

            } else {
                passwordAgainEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinPasswordAgainBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_light)
                binding.joinPasswordAgainVisibleIv.visibility = View.GONE
                binding.joinPasswordAgainInvisibleIv.visibility = View.GONE
                binding.joinPasswordAgainNotice.visibility = View.GONE
            }
        })
        joinViewModel.name.observe(this, Observer{
            if (it.isNotEmpty()) {
                nicknameEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinNicknameBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_dark)

            } else {
                nicknameEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinNicknameBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_light)
                binding.conditionText.setTextColor(ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                ))
                binding.check5.setImageResource(R.drawable.ic_check_light)
                binding.conditionLength2.setTextColor(ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                ))
                binding.check6.setImageResource(R.drawable.ic_check_light)
                binding.joinNicknameIv21.visibility = View.VISIBLE
            }
        })
        joinViewModel.nicknameValidation.observe(this, Observer{
            // 닉네임이 영어, 한글 문자, 그리고 숫자로만 이루어져 있는지 확인
            if (it.characters) {
                binding.conditionText.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check5.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionText.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check5.setImageResource(R.drawable.ic_check_light)
            }

            // 닉네임의 길이가 1에서 10자 사이인지 확인
            if (it.length) {
                binding.conditionLength2.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check6.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionLength2.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check6.setImageResource(R.drawable.ic_check_light)
            }
            // 닉네임이 유효한지 확인
            if (it.valid) {
                binding.joinNicknameIv21.visibility = View.INVISIBLE
                binding.joinNicknameIv22.visibility = View.VISIBLE
            }else {
                binding.joinNicknameIv21.visibility = View.VISIBLE
                binding.joinNicknameIv22.visibility = View.GONE
            }
        })  // 닉네임 유효성 검사
        joinViewModel.pwValidation.observe(this, Observer{
            // 비밀번호가 영문자를 포함하는지 확인
            if (it.englishChars) {
                binding.conditionEng.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check1.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionEng.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check1.setImageResource(R.drawable.ic_check_light)
            }

            // 비밀번호가 특수문자를 포함하는지 확인
            if (it.specialChars) {
                binding.conditionCharacter.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check2.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionCharacter.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check2.setImageResource(R.drawable.ic_check_light)
            }

            // 비밀번호가 숫자를 포함하는지 확인
            if (it.numbers) {
                binding.conditionNum.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check3.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionNum.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check3.setImageResource(R.drawable.ic_check_light)
            }

            // 비밀번호의 길이가 8자에서 20자 사이인지 확인
            if (it.length) {
                binding.conditionLength.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.check4.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionLength.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.check4.setImageResource(R.drawable.ic_check_light)
            }

            // 비밀번호 확인란과 일치하는지 확인
            if (it.isConfirmed){
                binding.joinPasswordAgainBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_green
                    )
                )
                binding.joinPasswordAgainNotice.setText("비밀번호가 일치해요")
                binding.joinPasswordAgainNotice.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_green
                    )
                )
                binding.joinPasswordAgainNotice.visibility = View.VISIBLE

            } else {
                binding.joinPasswordAgainBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_red
                    )
                )
                binding.joinPasswordAgainNotice.setText("비밀번호가 일치하지 않아요")
                binding.joinPasswordAgainNotice.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_red
                    )
                )
                binding.joinPasswordAgainNotice.visibility = View.VISIBLE
            }

            // 비밀번호가 유효한지 확인
            if(joinViewModel.pwValidation.value!!.valid) {
                binding.joinPasswordBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                    R.color.beering_green
                ))
            }
        })  // 패스워드 유효성 검사
        joinViewModel.idCheck.observe(this, Observer{
            when(it!!){
                JoinViewModel.Companion.DuplicationCheck.PROCEEDING -> {
                    binding.joinIdCheckedTv.visibility = View.GONE
                    binding.joinIdUncheckedTv.visibility = View.GONE
                }
                JoinViewModel.Companion.DuplicationCheck.UNCHECKED -> {
                    binding.joinIdCheckedTv.visibility = View.GONE
                    binding.joinIdUncheckedTv.visibility = View.VISIBLE
                }
                JoinViewModel.Companion.DuplicationCheck.CHECKED -> {
                    binding.joinIdCheckedTv.visibility = View.VISIBLE
                    binding.joinIdUncheckedTv.visibility = View.GONE
                }
            }
        })  // 아이디 중복여부
        joinViewModel.nicknameCheck.observe(this, Observer{
            Log.d("ititititit", it.toString())
            when(it!!){
                JoinViewModel.Companion.DuplicationCheck.PROCEEDING -> {
                    binding.conditionText.visibility = View.VISIBLE
                    binding.conditionLength2.visibility = View.VISIBLE
                    binding.check5.visibility = View.VISIBLE
                    binding.check6.visibility = View.VISIBLE
                    binding.joinNicknameCheck.visibility = View.GONE
                    binding.joinNicknameUncheck.visibility = View.GONE
                }
                JoinViewModel.Companion.DuplicationCheck.UNCHECKED -> {
                    binding.conditionText.visibility = View.GONE
                    binding.conditionLength2.visibility = View.GONE
                    binding.check5.visibility = View.GONE
                    binding.check6.visibility = View.GONE
                    binding.joinNicknameCheck.visibility = View.GONE
                    binding.joinNicknameUncheck.visibility = View.VISIBLE
                }
                JoinViewModel.Companion.DuplicationCheck.CHECKED -> {
                    binding.conditionText.visibility = View.GONE
                    binding.conditionLength2.visibility = View.GONE
                    binding.check5.visibility = View.GONE
                    binding.check6.visibility = View.GONE
                    binding.joinNicknameCheck.visibility = View.VISIBLE
                    binding.joinNicknameUncheck.visibility = View.GONE
                }
            }

        })  // 닉네임 중복여부
        joinViewModel.validNext.observe(this, Observer {
            when(it){
                true -> binding.joinNextOnIv.visibility = View.VISIBLE
                else -> binding.joinNextOnIv.visibility = View.GONE
            }
        })      // 다음 버튼 활성화
        joinViewModel.snackBarEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {msg ->
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
        })

        // EditText 값 있을 때만 버튼 활성화
        // 아이디
        userIdEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 입력값 담기
                joinViewModel.setUserId(userIdEdit.text.toString())

                // 아이디 유효성 검사

            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.joinIdIv22.setOnClickListener {
            // api 연결 후 중복 확인
            joinViewModel.checkId()
        }

        // 비밀번호
        passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinViewModel.setPassword(passwordEdit.text.toString())
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        // 비밀번호 토글 활성화
        binding.joinPasswordInvisibleIv.setOnClickListener {
            binding.joinPasswordEd.inputType = InputType.TYPE_CLASS_TEXT
            binding.joinPasswordInvisibleIv.visibility = View.GONE
            binding.joinPasswordVisibleIv.visibility = View.VISIBLE
        }
        binding.joinPasswordVisibleIv.setOnClickListener {
            binding.joinPasswordEd.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.joinPasswordInvisibleIv.visibility = View.VISIBLE
            binding.joinPasswordVisibleIv.visibility = View.GONE
        }
        binding.joinPasswordIv.setOnClickListener {
            binding.joinPasswordEd.text.clear()
            joinViewModel.setPassword("")
        }

        // 비밀번호 한번 더 입력
        passwordAgainEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinViewModel.setPasswordAgain(passwordAgainEdit.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        // 비밀번호 확인 x버튼
        binding.joinPasswordAgainIv.setOnClickListener {
            binding.joinPasswordAgainEd.text.clear()
            joinViewModel.setPasswordAgain("")
        }
        // 비밀번호 확인 눈 버튼
        binding.joinPasswordAgainInvisibleIv.setOnClickListener {
            binding.joinPasswordAgainEd.inputType = InputType.TYPE_CLASS_TEXT
            binding.joinPasswordAgainInvisibleIv.visibility = View.GONE
            binding.joinPasswordAgainVisibleIv.visibility = View.VISIBLE
        }
        binding.joinPasswordAgainVisibleIv.setOnClickListener {
            binding.joinPasswordAgainEd.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE
            binding.joinPasswordAgainVisibleIv.visibility = View.GONE
        }


        // 닉네임
        nicknameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinViewModel.setName(nicknameEdit.text.toString())

            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 닉네임 x 버튼
        binding.joinNicknameIv1.setOnClickListener {
            binding.joinNicknameEd.text.clear()
            joinViewModel.setName("")
        }
        // 닉네임 중복체크
        binding.joinNicknameIv22.setOnClickListener {
            joinViewModel.checkNickname()
        }

        // 다음 버튼
        binding.joinNextOnIv.setOnClickListener {
            val mIntent = Intent(this, TermActivity::class.java)
            mIntent.putExtra("id", joinViewModel.userId.value)
            mIntent.putExtra("pw", joinViewModel.password.value)
            mIntent.putExtra("name", joinViewModel.name.value)
            startActivity(mIntent)
        }
    }
}