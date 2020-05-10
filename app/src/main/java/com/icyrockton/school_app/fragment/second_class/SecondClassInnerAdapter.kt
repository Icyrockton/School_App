package com.icyrockton.school_app.fragment.second_class

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.SecondClassItemBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.学术科技与创新创业
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.心理素质与身体素质
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.思想政治与道德素养
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.文化沟通与交往能力
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.社会实践与志愿服务
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.社会工作与领导能力
import com.icyrockton.school_app.fragment.second_class.SecondClassFragment.Companion.艺术体验与审美修养

class SecondClassInnerAdapter(
    private val handler: SecondClassHandler,
    private val context: Context,
    private val data: List<SecondClassInfo>
) : RecyclerView.Adapter<SecondClassInnerAdapter.SecondClassInnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondClassInnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SecondClassInnerViewHolder(SecondClassItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: SecondClassInnerViewHolder, position: Int) {
        holder.bindData(handler, context, data[position])
    }

    class SecondClassInnerViewHolder(private val binding: SecondClassItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            handler: SecondClassHandler,
            context: Context,
            classInfo: SecondClassInfo
        ) {
            binding.data = classInfo
            if (!classInfo.isEnabled) {
                binding.secondClassGoToDetail.isEnabled = false
                binding.secondClassGoToDetail.text = classInfo.hintMsg
            } else {
                binding.secondClassGoToDetail.setOnClickListener {
                    handler.goToDetail(
                        classInfo.ID,
                        classInfo.course_name,
                        classInfo.credit,
                        true
                    )
                }
            }
            binding.secondClassHeaderImage.apply {
                when (classInfo.course_category) {
                    学术科技与创新创业 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_study))
                    思想政治与道德素养 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_political))
                    艺术体验与审美修养 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_art))
                    文化沟通与交往能力 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_communication))
                    心理素质与身体素质 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_work_out))
                    社会实践与志愿服务 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_volunteer))
                    社会工作与领导能力 -> setImageDrawable(context.getDrawable(R.drawable.ic_second_class_leader_ship))
                }
            }
        }

    }


}