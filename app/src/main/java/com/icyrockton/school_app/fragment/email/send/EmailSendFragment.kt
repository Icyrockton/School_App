package com.icyrockton.school_app.fragment.email.send

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
import com.icyrockton.school_app.databinding.EmailInboxFragmentBinding
import com.icyrockton.school_app.databinding.EmailSendFragmentBinding
import com.icyrockton.school_app.fragment.email.EmailHandler
import com.icyrockton.school_app.fragment.email.EmailViewModel
import com.icyrockton.school_app.fragment.email.inbox.EmailInboxAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EmailSendFragment : Fragment(), EmailHandler {

    companion object {
        fun newInstance() = EmailSendFragment()
    }
    private val viewModel: EmailViewModel by sharedViewModel()
    private lateinit var binding: EmailSendFragmentBinding
    private lateinit var adapter: EmailSendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=EmailSendFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= EmailSendAdapter(this, mutableListOf())
        binding.emailSendRecyclerview.layoutManager= LinearLayoutManager(requireContext())
        binding.emailSendRecyclerview.adapter=adapter
        viewModel.sendLiveData.observe(viewLifecycleOwner, Observer {
                data->
            when(data.networkType){
                NetworkType.DONE->{
                    binding.emailSendSwipeRefreshLayout.isRefreshing=false
                    adapter.updateData(data.data!!)
                }
                NetworkType.LOADING->{
                    binding.emailSendSwipeRefreshLayout.isRefreshing=true

                }
            }
        })
        binding.emailSendSwipeRefreshLayout.setOnRefreshListener { viewModel.refreshSendData() }
    }

    override fun goToDetail(message_ID: String) {

    }
}