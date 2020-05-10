package com.icyrockton.school_app.fragment.second_class.selected

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.icyrockton.school_app.databinding.SecondClassSelectedItemBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassSelectedInfo

class SecondClassSelectedAdapter(private val handler: SecondClassDeleteHandler,private var data: MutableList<SecondClassSelectedInfo>) :
    RecyclerView.Adapter<SecondClassSelectedAdapter.SecondClassSelectedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondClassSelectedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SecondClassSelectedViewHolder(
            SecondClassSelectedItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SecondClassSelectedViewHolder, position: Int) {
        holder.bindData(handler,data[position])
    }

    fun updateData(newData: List<SecondClassSelectedInfo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class SecondClassSelectedViewHolder(private val binding: SecondClassSelectedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(handler: SecondClassDeleteHandler,secondClassSelectedInfo: SecondClassSelectedInfo) {
            binding.data = secondClassSelectedInfo
            binding.secondClassDeleteBtn.setOnClickListener{
                handler.delete(secondClassSelectedInfo.delete_ID)
            }
            binding.secondClassSelectedImg.load(
                secondClassSelectedInfo.img_url
            ) {
                crossfade(true)
                transformations(RoundedCornersTransformation(0f,0f,10f,10f))
            }
        }


    }
}