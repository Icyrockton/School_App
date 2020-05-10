package com.icyrockton.school_app.fragment.second_class.score

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.SecondClassScoreQueryItemBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassScoreInfo
import com.icyrockton.school_app.fragment.second_class.SecondClassTermInfo


class SecondClassScoreQueryAdapter(
    private var data: MutableList<SecondClassScoreInfo>
) :
    RecyclerView.Adapter<SecondClassScoreQueryAdapter.SecondClassScoreQueryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondClassScoreQueryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SecondClassScoreQueryViewHolder(
            SecondClassScoreQueryItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: SecondClassScoreQueryViewHolder,
        position: Int
    ) {
        holder.bindData(data[position])
    }

    fun updateData(newData: List<SecondClassScoreInfo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class SecondClassScoreQueryViewHolder(private val binding: SecondClassScoreQueryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            secondClassScoreInfo: SecondClassScoreInfo
        ) {
            binding.data = secondClassScoreInfo
            if (secondClassScoreInfo.score=="缺勤" || secondClassScoreInfo.score=="不及格"){
                binding.textView125.setTextColor(Color.RED)
            }else{
                binding.textView125.setTextColor(Color.parseColor("#00fa9a"))
            }
        }


    }


}