package com.icyrockton.school_app.fragment.email.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import coil.ImageLoader
import com.icyrockton.school_app.R
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.databinding.FragmentEmailDetailBinding
import com.icyrockton.school_app.fragment.email.EmailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.wordpress.aztec.AztecAttributes
import org.wordpress.aztec.AztecText


class EmailDetailFragment : Fragment(), AztecText.OnImageTappedListener {


    private  var binding:FragmentEmailDetailBinding?=null
    private val viewmodel:EmailViewModel by sharedViewModel()
    private val imageLoader :ImageLoader by inject()
    private var isNavigationViewInit=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding==null){
            binding=FragmentEmailDetailBinding.inflate(inflater,container,false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isNavigationViewInit){
            NavigationUI.setupWithNavController(binding!!.emailDetailToolBar,findNavController())
            binding!!.emailDetailToolBar.title=arguments?.getString("title")!!
            val message_ID = arguments?.getString("message_ID")!!
            viewmodel.getEmailDetail(message_ID)
            binding!!.emailDetailAztecText.focusable=EditText.NOT_FOCUSABLE
            binding!!.emailDetailAztecText.imageGetter=CoilImageGetter(view,imageLoader,requireContext())
            initListener()
            viewmodel.htmlLiveData.observe(viewLifecycleOwner, Observer {
                    data->
                when(data.networkType){
                    NetworkType.LOADING->{
                        binding!!.emailDetailProgressBar.visibility=View.VISIBLE
                        binding!!.emailDetailAztecText.visibility=View.INVISIBLE
                    }
                    NetworkType.DONE->{
                        binding!!.emailDetailAztecText.fromHtml(data.data!!)
                        binding!!.emailDetailProgressBar.visibility=View.GONE
                        binding!!.emailDetailAztecText.visibility=View.VISIBLE
                    }
                }
            })
            isNavigationViewInit=true
        }


    }

    private fun initListener() {
        //图片事件监听器
        binding!!.emailDetailAztecText.setOnImageTappedListener(this)

    }

    override fun onImageTapped(attrs: AztecAttributes, naturalWidth: Int, naturalHeight: Int) {
        val imageUrl = attrs.getValue("src")
        findNavController().navigate(R.id.action_emailDetailFragment_to_imagePreviewDialogFragment,
        bundleOf("currentImageUrl" to imageUrl))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }
    companion object{
        private const val TAG = "EmailDetailFragment"
    }
}