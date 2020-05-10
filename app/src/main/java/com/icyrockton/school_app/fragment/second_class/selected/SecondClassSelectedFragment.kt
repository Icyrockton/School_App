package com.icyrockton.school_app.fragment.second_class.selected

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.SecondClassSelectedFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondClassSelectedFragment : Fragment(), SecondClassDeleteHandler {

    companion object {
        fun newInstance() = SecondClassSelectedFragment()
    }

    private val viewModel: SecondClassSelectedViewModel by viewModel()
    private lateinit var adapter: SecondClassSelectedAdapter
    private lateinit var binding: SecondClassSelectedFragmentBinding
    private var delete = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondClassSelectedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SecondClassSelectedAdapter(this, mutableListOf())
        NavigationUI.setupWithNavController(binding.secondClassSelectedToolbar, findNavController())
        binding.secondClassSelectedSwipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
            delete = false
        }
        binding.secondClassSelectedRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.secondClassSelectedRecyclerView.adapter = adapter
        viewModel.selectedLiveData.observe(
            viewLifecycleOwner, Observer { data ->
                when (data.networkType) {
                    NetworkType.LOADING -> {
                        binding.secondClassSelectedSwipeRefresh.isRefreshing = true
                        binding.secondClassSelectedNoContent.visibility=View.GONE
                    }
                    NetworkType.DONE -> {
                        binding.secondClassSelectedSwipeRefresh.isRefreshing = false
                        if (data.data!!.isEmpty()){
                            binding.secondClassSelectedNoContent.visibility=View.VISIBLE
                        }else{
                            binding.secondClassSelectedNoContent.visibility=View.GONE
                        }
                        adapter.updateData(data.data!!)

                        if (!delete) {
                            snackBar("刷新成功")
                        }
                    }
                }
            }
        )
        viewModel.deleteLiveData.observe(viewLifecycleOwner, Observer { data ->
            if (data.networkType == NetworkType.DONE) {
                val data = data.data
                if (data != null) {
                    if (data.flag) {
                        snackBar("删除成功!")
                        viewModel.refreshData()
                    } else {
                        snackBar(data.success)
                    }
                } else {
                    snackBar("未知错误!")

                }
            }
        })
    }

    override fun delete(deleteID: String) {
        delete = true
        viewModel.delete(deleteID)
    }

    private fun snackBar(msg: String) {
        Snackbar.make(binding.secondClassSelectedCoordinatorLayout, msg, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }
}