package com.icyrockton.school_app.fragment.score

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ScoreFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreFragment : Fragment() {

    companion object {
        fun newInstance() = ScoreFragment()
        private const val TAG = "ScoreFragment"
    }

    private val viewModel: ScoreViewModel by viewModel()
    private lateinit var binding: ScoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScoreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.scoreToolBar,findNavController())
        binding.scoreViewPager.adapter = ScoreFragmentAdapter(this)
        TabLayoutMediator(
            binding.scoreTabLayout,
            binding.scoreViewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "成绩总览"
                1 -> tab.text = "平时成绩"
                2 -> tab.text = "四六级成绩"
            }
        }.attach()
    }


}