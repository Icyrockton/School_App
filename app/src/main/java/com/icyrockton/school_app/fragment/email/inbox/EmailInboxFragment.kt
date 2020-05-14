package com.icyrockton.school_app.fragment.email.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.EmailInboxFragmentBinding
import com.icyrockton.school_app.fragment.email.EmailHandler
import com.icyrockton.school_app.fragment.email.EmailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EmailInboxFragment : Fragment(),EmailHandler {

    companion object {
        fun newInstance() = EmailInboxFragment()
    }

    private val viewModel: EmailViewModel by sharedViewModel()
    private lateinit var binding:EmailInboxFragmentBinding
    private lateinit var adapter: EmailInboxAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=EmailInboxFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= EmailInboxAdapter(this, mutableListOf())
        binding.emailInboxRecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.emailInboxRecyclerview.adapter=adapter
        viewModel.inboxLiveData.observe(viewLifecycleOwner, Observer {
            data->
                when(data.networkType){
                    NetworkType.DONE->{
                        binding.emailInboxSwipeRefreshLayout.isRefreshing=false
                        adapter.updateData(data.data!!)
                    }
                    NetworkType.LOADING->{
                        binding.emailInboxSwipeRefreshLayout.isRefreshing=true

                    }
                }
        })
        binding.emailInboxSwipeRefreshLayout.setOnRefreshListener { viewModel.refreshInboxData() }
    }

    override fun goToDetail(message_ID: String) {

    }
}