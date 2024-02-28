package com.example.beering.feature.auth.join.ui

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import com.example.beering.R
import com.example.beering.databinding.ActivityJoinBinding
import com.example.beering.util.base.BaseActivity
import com.example.beering.util.dpToPx

class JoinKakaoActivity: BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate) {
    private val joinViewModel : JoinViewModel by viewModels { JoinViewModel.Factory }


    override fun initAfterBinding() {
        binding.joinHeaderCl.toolbarBackIv.setOnClickListener {
            finish()
        }

        // ui재사용을 위한 초기 설정

        binding.joinIdCl.visibility =  View.GONE
        binding.joinPasswordCl.visibility =  View.GONE
        binding.joinPasswordAgainCl.visibility =  View.GONE

        val layoutParamsNicknme = binding.joinNicknameCl.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsNicknme.topMargin = 0
        binding.joinNicknameCl.layoutParams = layoutParamsNicknme



        val layoutParamsNextOn = binding.joinNextOnIv.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsNextOn.topMargin = dpToPx(500, this)
        binding.joinNextOnIv.layoutParams = layoutParamsNextOn

        val layoutParamsNextOff = binding.joinNextOffIv.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsNextOff.topMargin = dpToPx(500, this)
        binding.joinNextOffIv.layoutParams = layoutParamsNextOff



        val nicknameEdit = binding.joinNicknameEd
        joinViewModel.name.observe(this, Observer{
            if (it.isNotEmpty()) {
                nicknameEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.beering_black
                    )
                )
                binding.joinNicknameBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.beering_black
                    )
                )
                binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_dark)

            } else {
                nicknameEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.gray01
                    )
                )
                binding.joinNicknameBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.gray01
                    )
                )
                binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_light)
                binding.conditionText.setTextColor(
                    ContextCompat.getColor(
                    this@JoinKakaoActivity,
                    R.color.gray01
                ))
                binding.check5.setImageResource(R.drawable.ic_check_light)
                binding.conditionLength2.setTextColor(
                    ContextCompat.getColor(
                    this@JoinKakaoActivity,
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
                        this@JoinKakaoActivity,
                        R.color.beering_black
                    )
                )
                binding.check5.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionText.setTextColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.gray01
                    )
                )
                binding.check5.setImageResource(R.drawable.ic_check_light)
            }

            // 닉네임의 길이가 1에서 10자 사이인지 확인
            if (it.length) {
                binding.conditionLength2.setTextColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
                        R.color.beering_black
                    )
                )
                binding.check6.setImageResource(R.drawable.ic_check_dark)
            } else {
                binding.conditionLength2.setTextColor(
                    ContextCompat.getColor(
                        this@JoinKakaoActivity,
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
        joinViewModel.nicknameCheck.observe(this, Observer{
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
        })



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



        binding.joinNextOnIv.setOnClickListener {
            val mIntent = Intent(this, TermActivity::class.java)
            mIntent.putExtra("name", joinViewModel.name.value)
            startActivity(mIntent)
        }








    }


}