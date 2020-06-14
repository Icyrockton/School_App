package com.icyrockton.school_app.fragment.score.autoevaluation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.ScoreAutoEvaluationCourseItemBinding

class AutoEvaluationAdapter(private val list:MutableList<AutoEvaluationCourse>): RecyclerView.Adapter<AutoEvaluationAdapter.AutoEvaluationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoEvaluationViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return AutoEvaluationViewHolder(ScoreAutoEvaluationCourseItemBinding.inflate(layoutInflater,parent,false))
    }

    fun updateList(newData:List<AutoEvaluationCourse>){
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =list.size + 1


    override fun onBindViewHolder(holder: AutoEvaluationViewHolder, position: Int) {
        if (position==0) {
            holder.bindData(AutoEvaluationCourse("课程编号","课程名称",sid = "",lid = ""))
        }
        else
            holder.bindData(list[position-1])
    }

    class AutoEvaluationViewHolder(private val binding:ScoreAutoEvaluationCourseItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bindData(autoEvaluationCourse: AutoEvaluationCourse){
            binding.course=autoEvaluationCourse
        }
    }

}