package com.icyrockton.school_app.dialogfragment.image

import android.app.Dialog
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.ImageLoader
import coil.request.GetRequest
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ImagePreviewBinding
import com.icyrockton.school_app.fragment.email.EmailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.experimental.property.inject
import java.io.File

class ImagePreviewDialogFragment : DialogFragment() {

    private val viewmodel: EmailViewModel by sharedViewModel()
    private var currentImageUrl = ""
    private lateinit var adapter: ImageAdapter
    private lateinit var binding: ImagePreviewBinding
    private val imageLoader:ImageLoader by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImagePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.FullScreenDialogFragment).apply {
            setCanceledOnTouchOutside(true)
            window?.let(::setWindow)
        }
    }

    fun setWindow(win: Window) {   //设置全屏dialog fragment
        win.decorView.setPadding(0, 0, 0, 0)
        val lp = win.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        win.attributes = lp
        win.setGravity(Gravity.CENTER)
        win.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var currentIndex = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentImageUrl = arguments?.getString("currentImageUrl")!!
        adapter = ImageAdapter(mutableListOf())
        binding.imagePreviewViewpager.adapter = adapter
        binding.imagePreviewViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
                binding.imageCurrentIndex.text = (position + 1).toString()
            }
        })
        viewmodel.imageUrlLiveData.observe(viewLifecycleOwner, Observer { data ->
            adapter.updateData(data)
            val index = data.indexOf(currentImageUrl)
            binding.imagePreviewViewpager.setCurrentItem((if (index >= 0) index else 0), false)
            binding.imageTotalIndex.text = data.size.toString()
        })
        binding.imageDownload.setOnClickListener { initImageSave() }
    }

    private fun initImageSave() {//图片保存
        val imageUrl = viewmodel.getImageUrl(currentIndex)
        Log.d(TAG, "initImageSave: $imageUrl")
        imageUrl?.let {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    val startIndex=imageUrl.lastIndexOf('/')+1
                    val endIndex=imageUrl.lastIndexOf('.')
                    val title=imageUrl.substring(startIndex,endIndex)

                    val request = GetRequest.Builder(requireContext()).data(imageUrl).build()
                    val result = imageLoader.execute(request)
                    result.drawable?.let {
                        val saveUri = saveImage(it as BitmapDrawable, title)
                        withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(),"图片已保存 ${saveUri.path}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }


    suspend fun saveImage(bitmapDrawable: BitmapDrawable, title: String): Uri = withContext(Dispatchers.IO) {//保存图片
        val bitmap = bitmapDrawable.bitmap
        val imageUrl = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver, bitmap,
            title, ""
        )
        return@withContext Uri.parse(imageUrl)
    }

    companion object {
        private const val TAG = "ImagePreviewDialogFragm"
    }
}