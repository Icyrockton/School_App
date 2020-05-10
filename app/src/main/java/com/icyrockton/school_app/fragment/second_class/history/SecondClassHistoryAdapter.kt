package com.icyrockton.school_app.fragment.second_class.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.SecondClassHistoryItemBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassHandler
import com.icyrockton.school_app.fragment.second_class.SecondClassHistory


class SecondClassHistoryAdapter(
    private val handler: SecondClassHandler,
    private var data: MutableList<SecondClassHistory>
) :
    RecyclerView.Adapter<SecondClassHistoryAdapter.SecondClassHistoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondClassHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SecondClassHistoryViewHolder(
            SecondClassHistoryItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SecondClassHistoryViewHolder, position: Int) {
        holder.bindData(handler, data[position])
    }

    fun updateData(newData: List<SecondClassHistory>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class SecondClassHistoryViewHolder(private val binding: SecondClassHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(handler: SecondClassHandler, secondClassHistory: SecondClassHistory) {
            binding.data = secondClassHistory
            binding.secondClassHistoryImg.load(secondClassHistory.img_url) {
                crossfade(true)
                transformations(RoundedCornersTransformation(0f, 5f, 5f, 0f))
            }
            binding.secondClassHistoryGoToDetail.setOnClickListener {
                handler.goToDetail(
                    secondClassHistory.ID,
                    secondClassHistory.course_name,
                    secondClassHistory.credit,
                    false
                )
            }

        }


    }
}