package com.icyrockton.school_app.fragment.email.inbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.EmailItemBinding
import com.icyrockton.school_app.fragment.email.Email
import com.icyrockton.school_app.fragment.email.EmailHandler

class EmailInboxAdapter(private val handler: EmailHandler, private var data:MutableList<Email>): RecyclerView.Adapter<EmailInboxAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return EmailViewHolder(
            EmailItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bindData(handler,data[position])
    }

    fun updateData(newData:List<Email>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class EmailViewHolder(private val binding:EmailItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(handler: EmailHandler, email: Email) {
            binding.data=email
            //未读
           if (!email.read)
               binding.emailReadImg.visibility= View.INVISIBLE
        }

    }
}