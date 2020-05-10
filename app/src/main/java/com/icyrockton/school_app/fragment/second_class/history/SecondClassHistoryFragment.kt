package com.icyrockton.school_app.fragment.second_class.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.SecondClassHistoryFragmentBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondClassHistoryFragment : Fragment(), SecondClassHandler {

    companion object {
        fun newInstance() = SecondClassHistoryFragment()
    }

    private val viewModel: SecondClassHistoryViewModel by viewModel()
    private lateinit var binding: SecondClassHistoryFragmentBinding
    private lateinit var adapter: SecondClassHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondClassHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.secondClassHistoryToolbar,findNavController())
        adapter= SecondClassHistoryAdapter(this, mutableListOf())
        binding.secondClassHistoryRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.secondClassHistoryRecyclerView.adapter=adapter
        binding.secondClassHistorySwipeRefresh.setOnRefreshListener { viewModel.refreshData() }
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer {
            data->
            when(data.networkType){
                NetworkType.DONE->{
                    binding.secondClassHistorySwipeRefresh.isRefreshing=false
                    adapter.updateData(data.data!!)
                }
                NetworkType.LOADING->{
                    binding.secondClassHistorySwipeRefresh.isRefreshing=true
                }
            }
        })
    }

    override fun goToDetail(ID: String, course_name: String, credit: String,isEnable:Boolean) {
        findNavController().navigate(
            R.id.action_secondClassHistoryFragment_to_secondClassDetailFragment,
            bundleOf("ID" to ID, "course_name" to course_name, "credit" to credit,"isEnable" to isEnable)
        )
    }
    private fun snackBar(msg: String) {
        Snackbar.make(binding.secondClassHistoryCoordinatorLayout, msg, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }
}