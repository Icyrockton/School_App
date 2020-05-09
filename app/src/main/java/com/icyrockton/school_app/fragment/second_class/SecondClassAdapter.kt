package com.icyrockton.school_app.fragment.second_class

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.SecondClassInnerItemBinding

class SecondClassAdapter(private val handler: SecondClassHandler,private val context: Context,private var data:MutableList<SecondClassWrapper>) : RecyclerView.Adapter<SecondClassAdapter.SecondClassViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondClassViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return SecondClassViewHolder(SecondClassInnerItemBinding.inflate(inflater,parent,false))
    }

    override fun getItemCount(): Int=data.size

    override fun onBindViewHolder(holder: SecondClassViewHolder, position: Int) {
        holder.bindData(handler,context,data[position])
    }

    fun updateData(newData: List<SecondClassWrapper>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class SecondClassViewHolder(private val binding:SecondClassInnerItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            handler: SecondClassHandler,
            context: Context,
            secondClassWrapper: SecondClassWrapper
        ) {
            binding.secondClassInnerRecyclerView.layoutManager=LinearLayoutManager(
                context,LinearLayoutManager.HORIZONTAL,false
            )
            binding.secondClassInnerTitle.text=secondClassWrapper.title

            binding.secondClassInnerRecyclerView.adapter=SecondClassInnerAdapter(handler,context,secondClassWrapper.data)
        }
    }
}