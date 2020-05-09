package com.icyrockton.school_app.fragment.main.homepage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icyrockton.school_app.R

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
        private const val TAG = "HomePageFragment"
    }

    init {
        Log.d(TAG, "HomePageFragment 产生了")
    }

    private lateinit var viewModel: HomePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}