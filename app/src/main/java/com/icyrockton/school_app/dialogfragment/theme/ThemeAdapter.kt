package com.icyrockton.school_app.dialogfragment.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.base.ThemeHelper
import com.icyrockton.school_app.base.ThemeWrapper
import com.icyrockton.school_app.databinding.ThemeItemBinding

class ThemeAdapter(
    var currentChoice: Int,
    private val handler: ThemeHandler,
    private val context: Context
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ThemeViewHolder(ThemeItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = ThemeHelper.themes.size

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.binData(ThemeHelper.themes[position], position == currentChoice,handler,position,context)
    }

    class ThemeViewHolder(private val binding: ThemeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(
            themeWrapper: ThemeWrapper,
            isCheck: Boolean,
            handler: ThemeHandler,
            position: Int,
            context: Context
        ) {
            binding.themeName.text = themeWrapper.themeName
            binding.themeCheck.visibility = if (isCheck) View.VISIBLE else View.GONE
            binding.cardView.setCardBackgroundColor(context.getColor(themeWrapper.colorPrimary))
            binding.item.setOnClickListener {
                handler.checkCurrentTheme(position)
            }
        }

    }
}