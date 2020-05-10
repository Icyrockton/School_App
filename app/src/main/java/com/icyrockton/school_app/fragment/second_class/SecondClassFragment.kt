package com.icyrockton.school_app.fragment.second_class

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.SecondClassFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SecondClassFragment : Fragment(), SecondClassHandler,View.OnClickListener {

    companion object {
        fun newInstance() = SecondClassFragment()
        const val 学术科技与创新创业 = "学术科技与创新创业"
        const val 思想政治与道德素养 = "思想政治与道德素养"
        const val 艺术体验与审美修养 = "艺术体验与审美修养"
        const val 文化沟通与交往能力 = "文化沟通与交往能力"
        const val 心理素质与身体素质 = "心理素质与身体素质"
        const val 社会实践与志愿服务 = "社会实践与志愿服务"
        const val 社会工作与领导能力 = "社会工作与领导能力"
        private const val TAG = "SecondClassFragment"

    }

    private lateinit var adapter: SecondClassAdapter
    private  var binding: SecondClassFragmentBinding? =null
    private val viewModel: SecondClassViewModel by sharedViewModel()
    private var isNavigationViewInit=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding ==null) {
            binding = SecondClassFragmentBinding.inflate(inflater, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isNavigationViewInit) {
            initListener()
            binding?.let {
                it.secondClassRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter = SecondClassAdapter(this, requireContext(), mutableListOf())
                adapter.stateRestorationPolicy=RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                it.secondClassRecyclerView.adapter = adapter
                viewModel.secondClassInfoLivedata.observe(viewLifecycleOwner, Observer { data ->
                    adapter.updateData(data)
                })
            }
        }
    }

    private fun initListener() {
        binding?.let {
            it.secondClassBtnSelectedCourse.setOnClickListener(this)
            it.secondClassBtnCreditDistribution.setOnClickListener(this)
            it.secondClassBtnHistorySelect.setOnClickListener(this)
            it.secondClassBtnScoreQuery.setOnClickListener(this)
        }
    }

    override fun goToDetail(ID: String, course_name: String, credit: String,isEnable:Boolean) {
        findNavController().navigate(
            R.id.action_secondClassFragment_to_secondClassDetailFragment, bundleOf(
                "ID" to ID, "course_name" to course_name,"credit" to credit,"isEnable" to isEnable
            )
        )
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id){
                R.id.second_class_btn_selected_course->{
                    findNavController().navigate(R.id.action_secondClassFragment_to_secondClassSelectedFragment)
                }
                R.id.second_class_btn_history_select->{
                    findNavController().navigate(R.id.action_secondClassFragment_to_secondClassHistoryFragment)
                }
            }
        }
    }
}