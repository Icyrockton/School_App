package com.icyrockton.school_app.fragment.score.cet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.CetScoreItemBinding

class CatScoreAdapter(var data: List<CetScore>) :
    RecyclerView.Adapter<CatScoreAdapter.CetScoreViewHolder>() {
        companion object{
            private const val animationDuration = 700L
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CetScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CetScoreViewHolder(CetScoreItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CetScoreViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    class CetScoreViewHolder(private val binding: CetScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(score: CetScore) {
            binding.ID.text = "准考证:${score.ID}"
            binding.examName.text = score.exam_name
            binding.campus.text="校区:${score.campus.substring(score.campus.indexOf('西'))}"
            binding.examDate.text="考试时间:${score.exam_date}"
            binding.score.text="总分:${score.score}"
            val set= linkedMapOf(
                "听力:${score.listening_score}" to score.listening_score.toFloat(),
                "阅读:${score.read_score}" to score.read_score.toFloat(),
                "作文:${score.composition_score}" to score.composition_score.toFloat()
            )
            if (score.comprehensive_score.toDouble()!=0.0)
                set.put("综合:${score.comprehensive_score}",score.comprehensive_score.toFloat())
            binding.scoreRatioChart.animation.duration=animationDuration
            binding.scoreRatioChart.animate(set)
        }

    }
}