package com.example.beering.feature.auth.join.ui

import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.CheckBox
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.beering.R
import com.example.beering.databinding.ActivityJoinTermBinding
import com.example.beering.util.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class TermActivity : BaseActivity<ActivityJoinTermBinding>(ActivityJoinTermBinding::inflate) {
    val termViewModel : TermViewModel by viewModels { TermViewModel.Factory }
    override fun initAfterBinding() {

        termViewModel.curTermIndex.observe(this, Observer {
            var scriptId = 0
            when(it){
                0 -> scriptId = R.raw.term_first_script
                1 -> scriptId = R.raw.term_second_script
                2 -> scriptId = R.raw.term_third_script
            }
            val htmlContent = readHtmlFile(scriptId)
            binding.termScriptTv.text = Html.fromHtml(htmlContent)
        })
        termViewModel.checkBoxes.observe(this, Observer{
            binding.termCheckFirstCb.isChecked = it[0]
            binding.termCheckSecondCb.isChecked = it[1]
            binding.termCheckThirdCb.isChecked = it[2]
            if (it[0] && it[1] && it[2]){
                binding.termJoinOnIv.visibility = View.VISIBLE
                binding.termCheckAllCb.isChecked = true
            } else {
                binding.termJoinOnIv.visibility = View.GONE
                binding.termCheckAllCb.isChecked = false
            }
        })
        termViewModel.intentFlag.observe(this, Observer {
            it.getContentIfNotHandled()?.let{msg ->
                when(msg){
                    "complete" -> startActivity(Intent(this, CompleteActivity::class.java))
                    "request-error" -> {
                        Snackbar.make(binding.root, "요청값이 잘못되었습니다. 회원가입 정보를 다시 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    super.onDismissed(transientBottomBar, event)
                                    finish() // Snackbar가 완전히 사라진 후에 finish() 호출
                                }
                            }).show()
                    }
                    "request-email-valid-error" -> {
                        Snackbar.make(binding.root, "이메일 형식이 잘못되었습니다. 회원가입 정보를 다시 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    super.onDismissed(transientBottomBar, event)
                                    finish() // Snackbar가 완전히 사라진 후에 finish() 호출
                                }
                            }).show()
                    }
                }
            }
        })

        // 뒤로가기 버튼 로직 재정의
        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.termSlidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    closePanel()
                } else {
                    finish()
                }
            }
        }
        // slidingUpPanel 숨겨지게 초기화
        binding.termSlidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        // Back 버튼
        binding.termHeaderCl.toolbarBackIv.setOnClickListener {
            finish()
        }

        // 각 이용약관 선택했을 때 해당 약관내용 HTML 파일을 읽어서 문자열로 변환
        binding.termFirstLl.setOnClickListener{
            termViewModel.setTermIndex(0)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
        }
        binding.termSecondLl.setOnClickListener{
            termViewModel.setTermIndex(1)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
        }
        binding.termThirdLl.setOnClickListener{
            termViewModel.setTermIndex(2)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
        }

        // 이용약관 아래쪽 동의하기 버튼
        binding.termAgreeIv.setOnClickListener {
            termViewModel.agreeTerm()
            onBackPressedCallback.handleOnBackPressed()
        }

        // 체크박스 로직
        binding.termCheckFirstCb.setOnClickListener {
            termViewModel.toggleCheckBox(0)
        }
        binding.termCheckSecondCb.setOnClickListener {
            termViewModel.toggleCheckBox(1)
        }
        binding.termCheckThirdCb.setOnClickListener {
            termViewModel.toggleCheckBox(2)
        }
        binding.termCheckAllCb.setOnClickListener {view ->
            val it = view as CheckBox
            termViewModel.checkAll(it.isChecked)
        }

        // 회원가입 버튼
        binding.termJoinOnIv.setOnClickListener {
            // TODO : 회원가입 요청
            val id = intent.getStringExtra("id")!!
            val pw = intent.getStringExtra("pw")!!
            val name = intent.getStringExtra("name")!!
            termViewModel.signUp(id, pw, name)
            val mIntent = Intent(this, CompleteActivity::class.java)
            startActivity(mIntent)
        }

        // 뒤로가기 버튼 로직 재정의
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    private fun closePanel(){
        binding.termSlidingLayout.apply{
            panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
        binding.termScriptSv.scrollTo(0, 0)
    }

    private fun readHtmlFile(resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        try {
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n") // 줄바꿈 추가
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}