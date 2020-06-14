package com.icyrockton.school_app.fragment.score.autoevaluation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.AutoEvaluationFragmentBinding
import com.icyrockton.school_app.utils.getThemeColor
import com.jaeger.library.StatusBarUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope as koinAndroidxScopeLifecycleScope
import androidx.lifecycle.lifecycleScope as androidXLifecycleScope

class AutoEvaluationFragment : Fragment() {


    private val viewModel: AutoEvaluationViewModel by inject()
    private lateinit var adapter: AutoEvaluationAdapter
    private lateinit var binding: AutoEvaluationFragmentBinding
    private var score="5.0" //评价分
    private var content="" //评价内容
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AutoEvaluationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        StatusBarUtil.setColor(requireActivity(),requireContext().getThemeColor(R.attr.colorPrimaryDark),0)
        //设置下拉adapter
        binding.autoEvaluationPostProgress.visibility=View.INVISIBLE
        binding.autoEvaluationContent.setText("由${getString(R.string.app_name)}自动填写~")
        binding.autoEvaluationChoiceDropdown.apply {
            setAdapter(
                ArrayAdapter<CharSequence>(
                    requireContext(),
                    R.layout.score_auto_evaluation_dropdown_item,
                    resources.getStringArray(R.array.auto_evaluation_score)
                )
            )
            setText(adapter.getItem(0).toString(),false)
            setOnItemClickListener { _, _, position,_ ->
                when(position){
                    0-> score="5.0"
                    1-> score="4.0"
                    2-> score="3.0"
                    3-> score="2.0"
                    4-> score="1.0"
                }
            }
        }
        NavigationUI.setupWithNavController(binding.materialToolbar,findNavController())
        binding.autoEvaluationChoiceDropdown.listSelection=0
        adapter=AutoEvaluationAdapter(mutableListOf())
        binding.autoEvaluationRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.autoEvaluationRecyclerView.adapter=adapter
        binding.autoEvaluationPost.setOnClickListener { post() }
        viewModel.evaluationCourses.observe(viewLifecycleOwner, Observer {
            when(it.networkType){
                NetworkType.LOADING->{
                }
                NetworkType.DONE->{
                    binding.autoEvaluationPostProgress.max=it!!.data!!.size
                    binding.autoEvaluationPostProgress.progress = 0
                    adapter.updateList(it.data!!)
                }
            }
        })
        viewModel.evaluationPostProgress.observe(viewLifecycleOwner, Observer {
            when(it.networkType){
                NetworkType.LOADING->{  //正在完成某几个任务
                    binding.autoEvaluationPost.apply {
                        text="正在自动填写第 ${it.data!!} / ${viewModel.totalSize} 个课程"
                    }
                }
                NetworkType.DONE->{
                    binding.autoEvaluationPost.text="填写完毕~ 将在3S后返回主页"
                    androidXLifecycleScope.launch {
                        delay(3000L)
                        findNavController().navigate(R.id.action_autoEvaluationFragment_to_mainFragment)
                    }
                }
            }
            binding.autoEvaluationPostProgress.setProgress(it.data!!,true)
        })
    }

    private fun post() {

        binding.autoEvaluationPostProgress.visibility=View.VISIBLE
        binding.autoEvaluationPost.isEnabled=false
        viewModel.post(score,binding.autoEvaluationContent.text.toString())
    }

}