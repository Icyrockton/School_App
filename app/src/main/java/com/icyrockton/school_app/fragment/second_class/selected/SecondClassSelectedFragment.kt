package com.icyrockton.school_app.fragment.second_class.selected

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.icyrockton.school_app.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondClassSelectedFragment : Fragment() {

    companion object {
        fun newInstance() = SecondClassSelectedFragment()
    }

    private val viewModel :SecondClassSelectedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_class_selected_fragment, container, false)
    }



}