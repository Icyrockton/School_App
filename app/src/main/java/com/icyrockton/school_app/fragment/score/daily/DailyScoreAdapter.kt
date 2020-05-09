package com.icyrockton.school_app.fragment.score.daily

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.DailyScoreItemBinding

class DailyScoreAdapter( var data: List<DailyScoreWrapper>,private val context: Context) :
    RecyclerView.Adapter<DailyScoreAdapter.DailyScoreViewHolder>() {


    class DailyScoreViewHolder(private val context: Context,private val binding: DailyScoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: DailyScoreWrapper) {
            binding.courseName.text=data.course_name
            binding.classNumber.text="${data.class_number}班"
            binding.code.text=data.code
            binding.teacherName.text=if (data.teacher_name.length>3)data.teacher_name.substring(0,3)else data.teacher_name

            var firstIndex = data.summary.indexOf('：')
            var secondIndex=data.summary.indexOf('分',firstIndex)
            //未折算成绩

            val total_score=data.summary.substring(firstIndex+1,secondIndex)
            val thirdIndex = data.summary.indexOf('比', secondIndex)
            val fourthIndex = data.summary.indexOf('，',thirdIndex)
            //平时成绩比例
            val propotion=data.summary.substring(thirdIndex,fourthIndex)
            firstIndex=data.summary.indexOf('：',fourthIndex)
            secondIndex=data.summary.indexOf('分',fourthIndex)
            val real_score=data.summary.substring(firstIndex+1,secondIndex)

            binding.scoreHint.text="平时成绩占比${propotion}"
            binding.realScore.text="折算后${real_score}"
            binding.totalScore.text="总成绩${total_score}"

            binding.dailyScoreInnerRecyclerview.layoutManager=LinearLayoutManager(context)

            //设置分割线
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                    context.getDrawable(R.drawable.daily_score_divider)?.let { setDrawable(it) }
                }
            binding.dailyScoreInnerRecyclerview.addItemDecoration(dividerItemDecoration)
            binding.dailyScoreInnerRecyclerview.adapter=DailyScoreInnerAdapter(data.data)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DailyScoreViewHolder(context,DailyScoreItemBinding.inflate(inflater,parent,false))
    }

    override fun getItemCount(): Int =
        data.size

    override fun onBindViewHolder(holder: DailyScoreViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    companion object{
        private const val TAG = "DailyScoreAdapter"
    }
}