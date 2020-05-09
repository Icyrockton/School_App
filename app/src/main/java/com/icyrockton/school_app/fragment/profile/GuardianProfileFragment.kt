package com.icyrockton.school_app.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.FragmentGuardianProfileBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class GuardianProfileFragment : Fragment() {


    private lateinit var binding:FragmentGuardianProfileBinding
    private val viewModel:ProfileViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGuardianProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this
        binding.vm=viewModel
    }

}