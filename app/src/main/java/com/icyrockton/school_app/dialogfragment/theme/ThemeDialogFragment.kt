package com.icyrockton.school_app.dialogfragment.theme


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.SharedPreferencesHelper
import com.icyrockton.school_app.base.ThemeHelper
import com.icyrockton.school_app.base.ThemeHelper.Companion.themes
import com.icyrockton.school_app.databinding.ThemeDialogFragmentBinding
import org.koin.android.ext.android.inject


class ThemeDialogFragment: BottomSheetDialogFragment(),ThemeHandler {
    private lateinit var binding:ThemeDialogFragmentBinding
    private val helper by inject<SharedPreferencesHelper>()
    private val themeHelper by  inject<ThemeHelper> ()
    private lateinit var adapter: ThemeAdapter





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding=ThemeDialogFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
        private var currentChoice=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        currentChoice=helper.getInt(getString(R.string.sh_current_theme_index),0)
        adapter= ThemeAdapter(currentChoice,this,requireContext())//当前选中的主题
        binding.themeRecyclerView.layoutManager=GridLayoutManager(requireContext(),2)
        binding.themeRecyclerView.adapter=adapter
        binding.themeApply.setOnClickListener { applyTheme() }
        refreshPreview()
    }

    companion object{
        private const val TAG = "ThemeDialogFragment"
    }

    private fun refreshPreview() {//刷新预览图
        binding.themeTopCardView.setCardBackgroundColor(requireContext().getColor(themes[currentChoice].colorPrimaryDark))
        val gradientDrawable=GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
            requireContext().getColor(themes[currentChoice].colorAccent),
            requireContext().getColor(themes[currentChoice].colorPrimaryDark)
        ))

        binding.themeGradient.setImageDrawable(gradientDrawable)
        binding.themeApply.backgroundTintList=requireContext().getColorStateList(
            themes[currentChoice].colorAccent
        )

    }

    private fun applyTheme() {
        helper.edit {
            putInt(getString(R.string.sh_current_theme_index),currentChoice)
        }
        dismiss()
        themeHelper.applyTheme(activity!!)
    }

    override fun checkCurrentTheme(newChoice: Int) {
        val previousChoice=this.currentChoice
        this.currentChoice=newChoice
        adapter.currentChoice=newChoice
        Log.d(TAG, "checkCurrentTheme: ${previousChoice}  ${newChoice}")
        adapter.notifyItemChanged(previousChoice)
        adapter.notifyItemChanged(newChoice)
        refreshPreview()
    }
}
