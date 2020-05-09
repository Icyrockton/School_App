package com.icyrockton.school_app.fragment.score.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.DailyScoreInnerItemBinding

class DailyScoreInnerAdapter(private val data:List<DailyScore>) : RecyclerView.Adapter<DailyScoreInnerAdapter.DailyScoreInnerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyScoreInnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DailyScoreInnerViewHolder(DailyScoreInnerItemBinding.inflate(inflater,parent,false))
    }

    override fun getItemCount(): Int=
        data.size


    override fun onBindViewHolder(holder: DailyScoreInnerViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    class DailyScoreInnerViewHolder(private val binding:DailyScoreInnerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(score: DailyScore) {
            binding.score.text=score.score
            binding.proportion.text=score.proportion
            binding.scoreName.text=score.score_name
            binding.submitDate.text=score.submit_date
        }

    }
}