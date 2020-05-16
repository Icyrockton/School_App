package com.icyrockton.school_app.dialogfragment.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ImagePreviewItemBinding

class ImageAdapter(private val img_url:MutableList<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    fun updateData(newData:List<String>){
        img_url.clear()
        img_url.addAll(newData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return ImageViewHolder(ImagePreviewItemBinding.inflate(inflater,parent,false))
    }

    override fun getItemCount(): Int =img_url.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(img_url[position])
    }

    class ImageViewHolder(private val binding:ImagePreviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(url: String) {
            binding.imagePreviewImg.load(url){
                crossfade(true)
                placeholder(R.drawable.ic_image_placeholder)
            }
        }

    }
}