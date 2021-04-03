package com.icyrockton.school_app.fragment.score.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.databinding.ScoreOverViewFragmentBinding
import com.icyrockton.school_app.utils.toast
import com.icyrockton.school_app.utils.toastLong
import org.koin.androidx.viewmodel.ext.android.viewModel

//成绩总览
class ScoreOverViewFragment : Fragment(),OrderTypeHandler {


    private var currentChoice=0
    private val viewModel: ScoreOverViewViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ScoreOverViewFragmentBinding
    private lateinit var adapter:ScoreOverViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScoreOverViewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefreshLayout()
        recyclerView=binding.scoreOverViewRecyclerView
        recyclerView.setItemViewCacheSize(100)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        adapter= ScoreOverViewAdapter(requireContext(), ScoreWrapper(ArrayList(),ScoreRatio(),ScoreAverage(0f,0,0f)),this)
        recyclerView.adapter=adapter
        viewModel.scoreLiveData.observe(viewLifecycleOwner,
            Observer { result   ->
                when(result.networkType){
                    NetworkType.LOADING->{
                        binding.scoreOverViewSwipeRefreshLayout.isRefreshing=true
                    }
                    NetworkType.DONE->{
                        binding.scoreOverViewSwipeRefreshLayout.isRefreshing=false//更新完毕
                        adapter.updateData(result.data!!)
                        var scoreAverage = result.data.scoreAverage
                        toastLong("保研课总分:${scoreAverage.totalScore},查阅到的总课程数:${scoreAverage.count},平均分:${scoreAverage.average}")
                    }
                    NetworkType.ERROR->{
                        toastLong("您还未完成课程评价\n正在导航至自动课程评价界面")
                        findNavController().navigate(R.id.action_scoreFragment_to_autoEvaluationFragment)
                    }
                }
        })
    }

    private fun initRefreshLayout() {
        val refreshLayout = binding.scoreOverViewSwipeRefreshLayout

        refreshLayout.setOnRefreshListener {
            when(currentChoice){
                0->orderSubmitDateAsc()
                1->orderSubmitDateDesc()
                2->orderTermValue()
                3->orderCourseName()
                4->orderCourseCode()
            }
        }
        refreshLayout.setColorSchemeColors(
            R.attr.colorPrimaryDark,
            R.attr.colorPrimary,
            R.attr.colorAccent)

    }

    override fun orderSubmitDateAsc() {
        currentChoice=0
        viewModel.refreshScoreListByDate(ScoreOverViewRepository.ORDER_BY_ASC)
    }

    override fun orderSubmitDateDesc() {
        currentChoice=1
        viewModel.refreshScoreListByDate(ScoreOverViewRepository.ORDER_BY_DESC)

    }

    override fun orderTermValue() {
        currentChoice=2
        viewModel.refreshScoreList(ScoreOverViewRepository.ORDER_BY_TERM_YEAR)
    }

    override fun orderCourseName() {
        currentChoice=3
        viewModel.refreshScoreList(ScoreOverViewRepository.ORDER_BY_COURSE_NAME)
    }

    override fun orderCourseCode() {
        currentChoice=4
        viewModel.refreshScoreList(ScoreOverViewRepository.ORDER_BY_COURSE_CODE)
    }


}