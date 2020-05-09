package com.icyrockton.school_app.fragment.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ProfileFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {


    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileViewPager.adapter = ProfileFragmentStateAdapter(this)
        val navController = findNavController()

        NavigationUI.setupWithNavController(binding.profileFragmentToolbar,navController)
        TabLayoutMediator(
            binding.profileFragmentTabLayout,
            binding.profileViewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "学籍信息"
                1 -> tab.text = "监护人信息"
            }
        }.attach()

        //初始化头部信息
        initUserInfoHeader()
    }

    private fun initUserInfoHeader() {
        viewModel.profileInfo.observe(viewLifecycleOwner, Observer {
            data->
            binding.profileUserName.text=data.student_name
            binding.profileUserID.text=data.student_ID
            binding.profileUserInstitute.text=data.institute
            binding.profileUserMajor.text=data.major
        })
    }

    class ProfileFragmentStateAdapter(private val fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BasicProfileFragment()
                1 -> GuardianProfileFragment()
                else -> BasicProfileFragment()
            }
        }
    }
}