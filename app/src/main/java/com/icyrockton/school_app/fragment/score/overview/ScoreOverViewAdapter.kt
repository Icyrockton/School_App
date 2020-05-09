package com.icyrockton.school_app.fragment.score.overview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView

import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ScoreOverViewHeaderItemBinding
import com.icyrockton.school_app.databinding.ScoreOverViewItemBinding
import com.icyrockton.school_app.databinding.ScoreOverViewSelectOrderBinding

class ScoreOverViewAdapter(
    private val context: Context,
    var scoreWrapper: ScoreWrapper,
    private val handler: OrderTypeHandler
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_ITEM = 2
        private const val TYPE_ORDER = 1//排序按钮
        private const val TYPE_HEADER = 0//头部 展示折线图
        private const val TYPE_FOOTER = 3//底部 结束
        private const val animationDuration = 700L
        private const val TAG = "ScoreOverViewAdapter"
    }

    class ScoreOverViewHolder(val binding: ScoreOverViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //item的holder
        fun bindData(scoreDetail: ScoreDetail) {

            //设置必修选修图片
            when (scoreDetail.property) {
                "必" -> binding.property.setImageResource(R.drawable.ic_obligatory)
                "选" -> binding.property.setImageResource(R.drawable.ic_elective)
            }

            binding.courseName.text = scoreDetail.course_name



            binding.code.text = scoreDetail.code
            binding.classNumber.text = scoreDetail.class_number

            var year =
                "${scoreDetail.academic_year}年/${if (scoreDetail.semester.toInt() == 1) "上学期" else "下学期"}"
            binding.yearSemester.text = year


            binding.teacherName.text =
                if (scoreDetail.teacher_name.length > 3)
                    scoreDetail.teacher_name.substring(0, 3) else scoreDetail.teacher_name


            val daily_score_index = scoreDetail.daily_score.indexOf('(')
            val final_exam_index = scoreDetail.final_exam.indexOf('(')

            //分数
            val daily_score = scoreDetail.daily_score.substring(0, daily_score_index).toFloat()
            val final_exam_score = scoreDetail.final_exam.substring(0, final_exam_index).toFloat()

            //占比
            val daily_propotion = scoreDetail.daily_score.substring(daily_score_index)
            val final_exam_propotion = scoreDetail.final_exam.substring(final_exam_index)

            val set = linkedMapOf(
                "平时${daily_propotion}:${daily_score}" to daily_score,
                "期末${final_exam_propotion}:${final_exam_score}" to final_exam_score
            )
            binding.credit.text = scoreDetail.credit
            binding.score.text = scoreDetail.score
            //todo  设置bar

            binding.scoreRatioChart.animation.duration = animationDuration
            binding.scoreRatioChart.animate(set)

        }
    }

    class ScoreOverViewHeaderHolder(val binding: ScoreOverViewHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //header的holder
        fun bindData(scoreRatio: ScoreRatio) {
            binding.scoreOverViewBarChart.animation.duration = animationDuration
            val set = linkedMapOf(
                "90-100分:(${scoreRatio.excellent})" to scoreRatio.excellent.toFloat(),
                "80-90分:(${scoreRatio.good})" to scoreRatio.good.toFloat(),
                "60-80分:(${scoreRatio.pass})" to scoreRatio.pass.toFloat(),
                "0-60分:(${scoreRatio.fail})" to scoreRatio.fail.toFloat()
            )
            //Log.d(TAG, "bindData: ${set}")
            binding.scoreOverViewBarChart.animate(set)
        }
    }

    class ScoreOverViewOrderHolder(val binding: ScoreOverViewSelectOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindHandler(context: Context, handler: OrderTypeHandler) {
            binding.scoreOverViewOrderTypeBtn.setOnClickListener { showPopUp(context, handler) }
        }

        private fun showPopUp(context: Context, handler: OrderTypeHandler) {
            val popupMenu = PopupMenu(context, binding.scoreOverViewOrderTypeBtn)
            popupMenu.menuInflater.inflate(R.menu.order_type_menu, popupMenu.menu)


            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {

                    R.id.btn_order_submitDate_desc -> {
                        handler.orderSubmitDateDesc()
                        binding.scoreOverViewOrderTypeBtn.text="提交时间降序"
                    }
                    R.id.btn_order_submitDate_asc -> {
                        handler.orderSubmitDateAsc()
                        binding.scoreOverViewOrderTypeBtn.text="提交时间升序"
                    }
                    R.id.btn_order_courseCode -> {
                        handler.orderCourseCode()
                        binding.scoreOverViewOrderTypeBtn.text="课程代码"
                    }
                    R.id.btn_order_courseName -> {
                        handler.orderCourseName()
                        binding.scoreOverViewOrderTypeBtn.text="课程名称"
                    }
                    R.id.btn_order_termValue -> {
                        handler.orderTermValue()
                        binding.scoreOverViewOrderTypeBtn.text="课程选修时间"
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            TYPE_HEADER -> {
                val binding = ScoreOverViewHeaderItemBinding.inflate(inflater, parent, false)
                return ScoreOverViewHeaderHolder(binding)
            }
            TYPE_ORDER -> {
                val binding = ScoreOverViewSelectOrderBinding.inflate(inflater, parent, false)
                return ScoreOverViewOrderHolder(binding)
            }
            TYPE_ITEM -> {
                val binding = ScoreOverViewItemBinding.inflate(inflater, parent, false)
                return ScoreOverViewHolder(binding)
            }
            else -> {
                val binding = ScoreOverViewItemBinding.inflate(inflater, parent, false)
                return ScoreOverViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return scoreWrapper.scoreList.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScoreOverViewHolder -> holder.bindData(scoreWrapper.scoreList[position - 2])
            is ScoreOverViewHeaderHolder -> holder.bindData(scoreWrapper.scoreRatio)
            is ScoreOverViewOrderHolder -> holder.bindHandler(context, handler)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            1 -> TYPE_ORDER
            else -> TYPE_ITEM
        }
    }

    fun updateData(newData: ScoreWrapper) {
        scoreWrapper.scoreRatio.apply {
            excellent = newData.scoreRatio.excellent
            good = newData.scoreRatio.good
            pass = newData.scoreRatio.pass
            fail = newData.scoreRatio.fail
        }

        scoreWrapper.scoreList.clear()

        scoreWrapper.scoreList.addAll(newData.scoreList)
        this.notifyDataSetChanged()
    }

}