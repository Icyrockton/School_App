package com.icyrockton.school_app.fragment.second_class.detail

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import coil.api.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.google.android.material.snackbar.Snackbar
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.SecondClassDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondClassDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SecondClassDetailFragment()
        private const val TAG = "SecondClassDetailFragme"
    }

    private var ID: String = ""
    private var course_name = ""
    private var credit = ""
    private val viewModel: SecondClassDetailViewModel by viewModel()
    private lateinit var binding: SecondClassDetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondClassDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ID = arguments?.getString("ID")!!
        course_name = arguments?.getString("course_name")!!
        credit = arguments?.getString("credit")!!

        viewModel.getData(ID, credit)
        NavigationUI.setupWithNavController(binding.secondClassDetailToolbar, findNavController())
        initLiveData()

    }

    private fun initLiveData() {
        viewModel.postResult.observe(viewLifecycleOwner, Observer { data ->
            data.data?.let {
                if (it.flag) {//成功
                    Snackbar.make(
                        binding.secondClassDetailCoordinatorLayout,
                        "报名成功!",
                        Snackbar.LENGTH_SHORT
                    ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setAction(getText(R.string.hint_i_know)) {
                    }
                        .show()

                } else {
                    Snackbar.make(
                        binding.secondClassDetailCoordinatorLayout,
                        it.error,
                        Snackbar.LENGTH_SHORT
                    ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setAction(getText(R.string.hint_cancel)) {
                    }
                        .show()

                }
                binding.secondClassBtnPost.isEnabled=true

            }
        })
        viewModel.detailInfo.observe(viewLifecycleOwner, Observer { data ->
            when (data.networkType) {
                NetworkType.LOADING -> {
                    binding.secondClassDetailProgress.visibility = View.VISIBLE
                    binding.secondClassDetailContent.visibility = View.INVISIBLE
                }
                NetworkType.DONE -> {
                    binding.secondClassDetailProgress.visibility = View.GONE
                    binding.data = data.data
                    binding.secondClassDetailImg.load(data.data!!.img_url) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(0f, 0f, 20f, 20f))
                    }
                    binding.secondClassDetailContent.visibility = View.VISIBLE
                    binding.secondClassBtnPost.setOnClickListener {
                        post()
                    }

                }
            }
        })
    }

    private fun post() {
        binding.secondClassBtnPost.isEnabled=false
        viewModel.post(ID)
    }


}