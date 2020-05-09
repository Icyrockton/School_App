package com.icyrockton.school_app.fragment.main.mainpage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = MainPageFragment()
        private const val TAG = "MainPageFragment"
    }
    init {
        Log.d(TAG, "MainPageFragment 产生了 ")
    }

    private lateinit var viewModel: MainPageViewModel
    private lateinit var navController:NavController
    private lateinit var binding: MainPageFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnQueryScore.setOnClickListener(this)
        binding.btnSecondClass.setOnClickListener(this)
        navController=Navigation.findNavController(requireActivity(), R.id.nav_host_activity_main)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_query_score->navController.navigate(R.id.scoreFragment)
            R.id.btn_second_class->navController.navigate(R.id.secondClassFragment)
        }
    }
}