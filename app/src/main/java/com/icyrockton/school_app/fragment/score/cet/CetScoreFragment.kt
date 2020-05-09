package com.icyrockton.school_app.fragment.score.cet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.CetScoreFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CetScoreFragment : Fragment() {

    companion object {
    }

    private val  viewModel: CetScoreViewModel by viewModel()
    private lateinit var adapter: CatScoreAdapter
    private lateinit var binding:CetScoreFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= CetScoreFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cetScoreRecyclerview.layoutManager=LinearLayoutManager(requireContext())
        adapter=CatScoreAdapter(emptyList())
        binding.cetScoreRecyclerview.adapter=adapter
        viewModel.scoreLiveData.observe(viewLifecycleOwner, Observer { data ->
            when (data.networkType) {
                NetworkType.LOADING -> {
                    binding.cetScoreSwipeRefreshLayout.isRefreshing=true
                }
                NetworkType.DONE -> {
                    binding.cetScoreSwipeRefreshLayout.isRefreshing=false
                    adapter.data = data.data!!
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }



}