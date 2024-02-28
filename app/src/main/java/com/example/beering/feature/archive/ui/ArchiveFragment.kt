package com.example.beering.feature.archive.ui

import android.util.Log
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.R
import com.example.beering.databinding.FragmentArchiveBinding
import com.example.beering.feature.archive.model.MonthlyReport
import com.example.beering.util.addStatusBarMarginTop
import com.example.beering.util.base.BaseFragment
import com.google.android.material.internal.ViewUtils.removeOnGlobalLayoutListener
import java.time.LocalDateTime
import java.util.Calendar


class ArchiveFragment : BaseFragment<FragmentArchiveBinding>(FragmentArchiveBinding::inflate) {
    override fun initAfterBinding() {
        val monthlyReports = listOf<MonthlyReport>(
            MonthlyReport(5.3f, 7),
            MonthlyReport(8f, 8),
            MonthlyReport(7.2f, 9),
            MonthlyReport(8.5f, 10),
            MonthlyReport(6f, 11),
            MonthlyReport(9.3f, 12)
        )

        binding.archiveTitleTv.addStatusBarMarginTop(requireContext())

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 월은 0부터 시작하므로 1을 더해줍니다.
        binding.archiveCalendarDateTv.text = requireContext().getString(R.string.archive_calendar_date, year, month)

        // matchConstraint의 경우 width, height를 가져오려면 뷰가 완벽하게 그려진 다음 measureWidth 매서드로 측정해야합니다.
        val vto: ViewTreeObserver = binding.archiveBarChartRv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val rvWidth = binding.archiveBarChartRv.measuredWidth   // View가 그려진 후 시스템에 의해 측정
                val rvHeight = binding.archiveBarChartRv.measuredHeight
                binding.archiveBarChartRv.apply {
                    val itemWidth = rvWidth / 6
                    val itemHeight = rvHeight * 0.7f
                    val mAdapter = BarChartRVAdapter(requireContext(), monthlyReports, itemWidth, itemHeight)
                    adapter = mAdapter
                    overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
                // 작업이 끝난 후 observer 해제 (중복 실행 방지)
                binding.archiveBarChartRv.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}