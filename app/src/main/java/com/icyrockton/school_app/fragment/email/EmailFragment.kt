package com.icyrockton.school_app.fragment.email

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.EmailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailFragment : Fragment() {

    companion object {

    }

    private  val viewModel: EmailViewModel by sharedViewModel()

    private lateinit var binding:EmailFragmentBinding
    private lateinit var adapter: EmailFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=EmailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter=EmailFragmentAdapter(this)
        binding.emailViewPager.adapter=adapter
        NavigationUI.setupWithNavController(binding.emailToolBar,findNavController())
        TabLayoutMediator(binding.emailTabLayout,binding.emailViewPager){
            tab: TabLayout.Tab, position: Int ->
            when(position){
                0->{
                    tab.text=getString(R.string.str_email_inbox)
                    tab.icon=requireContext().getDrawable(R.drawable.ic_email_inbox)
                }
                1->{
                    tab.text=getString(R.string.str_email_send)
                    tab.icon=requireContext().getDrawable(R.drawable.ic_email_send)
                }
            }
        }.attach()

    }


}