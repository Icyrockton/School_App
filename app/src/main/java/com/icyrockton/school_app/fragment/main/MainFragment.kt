package com.icyrockton.school_app.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import coil.api.load
import coil.transform.CircleCropTransformation
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.base.SharedPreferencesHelper
import com.icyrockton.school_app.base.ThemeHelper

import com.icyrockton.school_app.databinding.MainFragmentBinding
import com.icyrockton.school_app.databinding.MainFragmentDrawerHeaderBinding
import com.icyrockton.school_app.fragment.main.homepage.HomePageFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private val helper:SharedPreferencesHelper by inject()
    private val themeHelper:ThemeHelper by inject()
    private lateinit var binding: MainFragmentBinding
    private val viewModel:MainViewModel by viewModel()
    private lateinit var  fragmentNavController:NavController
    private lateinit var activityNavController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        const val TAG = "MainFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentNavController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_main)
        activityNavController=Navigation.findNavController(requireActivity(),R.id.nav_host_activity_main)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mainFragmentToolbar)

        //绑定底部导航栏
        NavigationUI.setupWithNavController(binding.mainBottomNavView,fragmentNavController)
        //绑定toolbar drawerLayout
        NavigationUI.setupWithNavController(binding.mainFragmentToolbar,fragmentNavController,binding.mainFragmentDrawerLayout)


        initDrawer()
        initDrawerHeader()
        initDrawerFooter()
    }

    private fun initDrawerFooter() {
        val darkMode = helper.getBoolean(getString(R.string.sh_dark_mode), false)
        binding.darkModeSwitch.isChecked=darkMode
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            helper.edit {
                putBoolean(getString(R.string.sh_dark_mode),isChecked)
            }
            themeHelper.applyTheme(requireActivity())
        }
    }

    private fun initDrawerHeader() {
        initThemeButton()
        initProfileButton()
    }

    private fun initProfileButton() {

        drawerHeaderBinding.drawerHeaderAvatarLayout.setOnClickListener {
            activityNavController.navigate(R.id.profileFragment)
        }
        drawerHeaderBinding.drawerHeaderBtnStuInfo.setOnClickListener {
            activityNavController.navigate(R.id.profileFragment)
        }
    }

    private fun initThemeButton() {
        drawerHeaderBinding.drawerHeaderBtnThemeChange.setOnClickListener {
            fragmentNavController.navigate(R.id.themeDialogFragment)
        }
    }


    private lateinit var drawerHeaderBinding: MainFragmentDrawerHeaderBinding
    private fun initDrawer() {
        val headerView = binding.mainFragmentNavView.getHeaderView(0)
        //绑定drawer header部分
        drawerHeaderBinding= MainFragmentDrawerHeaderBinding.bind(headerView)

        viewModel.scoreLiveData.observe(viewLifecycleOwner, Observer {
            data->
            when(data.networkType){
                NetworkType.LOADING-> {
                    drawerHeaderBinding.drawerHeaderAvatar.load(R.drawable.ic_home)
                }
                NetworkType.DONE->{
                    val stuInfo = data.data!!
                    drawerHeaderBinding.drawerHeaderStuName.text=stuInfo.stu_name
                    drawerHeaderBinding.drawerHeaderStuID.text=stuInfo.stuID
                    drawerHeaderBinding.drawerHeaderAvatar.load(stuInfo.img_avatar){
                        transformations(CircleCropTransformation())
                    }
                }

            }
        })

    }


}
