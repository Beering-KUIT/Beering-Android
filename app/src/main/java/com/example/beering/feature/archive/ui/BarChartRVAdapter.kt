package com.example.beering.feature.archive.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ClipDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.R
import com.example.beering.databinding.ItemArchiveBarChartBinding
import com.example.beering.feature.archive.model.MonthlyReport
import java.lang.Float.max

class BarChartRVAdapter(private val context : Context,
                        private val dataSet: List<MonthlyReport>,
                        private val itemWidth : Int,
                        private val itemHeight : Float) : RecyclerView.Adapter<BarChartRVAdapter.ViewHolder>() {

    private val maxQuantity: Float
    init {
        maxQuantity = getMaxQuantity()
    }
    inner class ViewHolder(private val binding : ItemArchiveBarChartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MonthlyReport) {
            // itemView의 width를 전체 6등분해서 할당
            val layoutParams = binding.root.layoutParams
            layoutParams.width = itemWidth
            binding.root.layoutParams = layoutParams

            binding.itemChartUnitTv.text = context.getString(R.string.archive_alcohol_unit, data.quantity)
            binding.itemChartMonthTv.text = context.getString(R.string.archive_month, data.month)

            if (data.quantity == maxQuantity){
                val color = context.getColor(R.color.calendar2) // 변경할 색상 리소스 가져오기

                binding.itemChartBarIv.setColorFilter(color)
            }
            // 막대그래프 높이 설정
            val barHeight = (data.quantity / maxQuantity) * itemHeight
            // ImageView 애니메이션 추가
            val animator = ValueAnimator.ofInt(0, barHeight.toInt())
            animator.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                binding.itemChartBarIv.layoutParams.height = value // 뷰의 높이 설정
                binding.itemChartBarIv.requestLayout() // 뷰의 레이아웃 갱신
            }
            animator.duration = 1000 // 애니메이션 지속 시간
            animator.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArchiveBarChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun getMaxQuantity(): Float{
        var maxHeight = 0f
        for (data in dataSet){
            maxHeight = max(maxHeight, data.quantity)
        }
        return maxHeight
    }
}