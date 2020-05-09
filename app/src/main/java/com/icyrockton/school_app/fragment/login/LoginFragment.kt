package com.icyrockton.school_app.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.SharedPreferencesHelper
import com.icyrockton.school_app.databinding.LoginFragmentBinding
import com.icyrockton.school_app.fragment.login.LoginResult
import com.icyrockton.school_app.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: LoginFragmentBinding
    private val sharedPreferencesHelper by inject<SharedPreferencesHelper>()
    private var isRemember = ObservableBoolean(false)
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.login_fragment,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.isRemember = isRemember
        //初始化 监听器
        initListener()

        //加载账号信息
        loadInfo()
        //请求SESSION ID

        //设置验证码点击事件
        binding.loginVerifyImage.setOnClickListener { refreshVerifyImage() }

        //设置验证码
        viewModel.verifyImage.observe(viewLifecycleOwner) { image ->
            binding.loginLoading.visibility = View.INVISIBLE//隐藏加载
            binding.loginVerifyImage.setImageBitmap(image)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            waitForlogin(it)
        }
    }


    private fun initListener() {
        binding.loginPasswordTextfield.setOnClickListener(this)
        binding.loginUserIDTextfield.setOnClickListener(this)
        binding.loginVerifyCodeTextfield.setOnClickListener(this)
    }

    fun refreshVerifyImage() {
        binding.loginLoading.visibility = View.VISIBLE//显示加载
        viewModel.refreshVerifyImage()
    }

    fun waitForlogin(loginResult: LoginResult) {
        val (loginMsg, status) = loginResult

        when (status.toInt()) {
            1 -> {//登录成功
                toast(loginMsg)
                saveInfo()
                findNavController().navigate(R.id.mainFragment)
            }
            2 -> {//登录错误 用户不存在
                toast(loginMsg)
                binding.loginUserIDLayout.error = getString(R.string.hint_userID_notfount)
            }
            5 -> {//登录失败 密码不正确
                toast(loginMsg)
                binding.loginUserPasswordLayout.error = getString(R.string.hint_userPassword_error)
            }
            -2 -> {// 验证码输入不正确
                toast(loginMsg)
                binding.loginVerifyCodeLayout.error = getString(R.string.hint_verifyCode_error)
            }
        }
        refreshVerifyImage()
    }

    private fun loadInfo() {

        val sh_isRemember = sharedPreferencesHelper.getBoolean(getString(R.string.sh_isRemember), false)
        if (sh_isRemember) {
            viewModel.userID = sharedPreferencesHelper.getString(getString(R.string.sh_userID), "")
            viewModel.userPassword = sharedPreferencesHelper.getString(getString(R.string.sh_userPassword), "")
            isRemember.set(sh_isRemember)
        }

    }

    private fun saveInfo() {//保存账号密码到SharedPreferences

        sharedPreferencesHelper.edit(commit = true) {
            putBoolean(getString(R.string.sh_isRemember), isRemember.get())
            if (isRemember.get()) {
                putString(getString(R.string.sh_userID), viewModel.userID)
                putString(getString(R.string.sh_userPassword), viewModel.userPassword)
            }
        }
    }

    override fun onClick(v: View?) {

        v?.let {
            when (v.id) {
                R.id.login_verifyCode_Layout, R.id.login_userPassword_Layout, R.id.login_userID_Layout,
                R.id.login_password_textfield, R.id.login_userID_textfield, R.id.login_verifyCode_textfield -> {
                    //清除错误信息
                    binding.loginUserIDLayout.error = null
                    binding.loginVerifyCodeLayout.error = null
                    binding.loginUserPasswordLayout.error = null
                }
            }
        }
    }
}
