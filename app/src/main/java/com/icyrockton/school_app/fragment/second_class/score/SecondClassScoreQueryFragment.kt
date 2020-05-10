package com.icyrockton.school_app.fragment.second_class.score

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.SecondClassScoreQueryFragmentBinding
import com.icyrockton.school_app.fragment.second_class.SecondClassTermInfo
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondClassScoreQueryFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() = SecondClassScoreQueryFragment()
    }


    private var term_name: String = "所有"
    private var term_id: String = "-1"
    private val viewModel: SecondClassScoreQueryViewModel by viewModel()
    private lateinit var binding: SecondClassScoreQueryFragmentBinding
    private lateinit var term_Info: List<SecondClassTermInfo>
    private lateinit var show_term_item:Array<CharSequence>
    private lateinit var adapter: SecondClassScoreQueryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondClassScoreQueryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SecondClassScoreQueryAdapter(mutableListOf())
        binding.secondClassScoreToolbar.setOnMenuItemClickListener(this)
        viewModel.getTermInfo()
        viewModel.termInfo.observe(viewLifecycleOwner, Observer {
            term_Info = it
            show_term_item=term_Info.map { it.term_name }.toTypedArray()
        })
        binding.secondClassScoreSwipeRefresh.setOnRefreshListener {
            viewModel.refreshData(
                term_name,
                term_id
            )
        }
        NavigationUI.setupWithNavController(binding.secondClassScoreToolbar, findNavController())

        binding.secondClassScoreNoContent.visibility = View.GONE
        binding.secondClassScoreRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.secondClassScoreRecyclerView.adapter = adapter
        viewModel.socreLiveData.observe(viewLifecycleOwner, Observer { data ->
            when (data.networkType) {
                NetworkType.LOADING -> {
                    binding.secondClassScoreSwipeRefresh.isRefreshing = true

                }
                NetworkType.DONE -> {
                    binding.secondClassScoreSwipeRefresh.isRefreshing = false
                    if (data.data != null) {
                        adapter.updateData(data.data)
                        if (data.data.isEmpty()) { //内容为空
                            binding.secondClassScoreNoContent.visibility = View.VISIBLE
                        } else {
                            binding.secondClassScoreNoContent.visibility = View.GONE
                        }
                        snackBar("刷新成功~")
                    } else {
                        snackBar("未知错误~")
                    }
                }

            }
        })
    }

    private fun snackBar(msg: String) {
        Snackbar.make(binding.secondClassScoreCoordinatorLayout, msg, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.second_class_score_select_term -> {
                MaterialAlertDialogBuilder(requireContext()).setTitle("学期选择")
                    .setItems(show_term_item) {
                            dialog: DialogInterface?, which: Int ->
                        dialog?.let {
                            term_name=term_Info[which].term_name
                            term_id=term_Info[which].term_id
                            it.dismiss()
                            viewModel.refreshData(term_name,term_id)
                        }
                    }.show()

            }
        }
        return true
    }


}