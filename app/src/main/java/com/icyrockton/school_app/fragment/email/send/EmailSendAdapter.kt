package com.icyrockton.school_app.fragment.email.send

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.databinding.EmailItemBinding
import com.icyrockton.school_app.databinding.EmailSendItemBinding
import com.icyrockton.school_app.fragment.email.Email
import com.icyrockton.school_app.fragment.email.EmailHandler
import com.icyrockton.school_app.fragment.email.SendEmail

class EmailSendAdapter(private val handler: EmailHandler, private var data:MutableList<SendEmail>): RecyclerView.Adapter<EmailSendAdapter.EmailSendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailSendViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return EmailSendViewHolder(
            EmailSendItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: EmailSendViewHolder, position: Int) {
        holder.bindData(handler,data[position])
    }

    fun updateData(newData:List<SendEmail>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class EmailSendViewHolder(private val binding: EmailSendItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(handler: EmailHandler, sendEmail: SendEmail) {
            binding.data=sendEmail
            //未读
            if (!sendEmail.read)
                binding.emailReadImg.visibility= View.INVISIBLE
        }

    }
}