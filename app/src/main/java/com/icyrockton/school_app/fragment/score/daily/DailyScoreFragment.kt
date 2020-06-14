package com.icyrockton.school_app.fragment.score.daily

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.DailyScoreFragmentBinding
import com.icyrockton.school_app.utils.toast
import com.icyrockton.school_app.utils.toastLong
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyScoreFragment : Fragment() {

    companion object {
        fun newInstance() = DailyScoreFragment()
    }

    private val viewModel: DailyScoreViewModel by viewModel()
    private lateinit var binding: DailyScoreFragmentBinding
    private lateinit var adapter: DailyScoreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DailyScoreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dailyScoreRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.dailyScoreSwipeRefreshLayout.setOnRefreshListener { viewModel.refreshData() }
        adapter = DailyScoreAdapter(emptyList(), requireContext())
        binding.dailyScoreRecyclerview.adapter=adapter
        viewModel.scoreLiveData.observe(viewLifecycleOwner, Observer { data ->
            when (data.networkType) {
                NetworkType.LOADING -> {
                    binding.dailyScoreSwipeRefreshLayout.isRefreshing=true
                }
                NetworkType.DONE -> {
                    binding.dailyScoreSwipeRefreshLayout.isRefreshing=false
                    adapter.data = data.data!!
                    adapter.notifyDataSetChanged()
                }
                NetworkType.ERROR->{
                    toastLong("您还未完成课程评价\n正在导航至自动课程评价界面")
                    findNavController().navigate(R.id.action_scoreFragment_to_autoEvaluationFragment)
                }
            }

        })
    }


}