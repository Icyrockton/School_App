package com.icyrockton.school_app.dialogfragment.image

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.ImageLoader
import coil.request.GetRequest
import coil.request.RequestResult
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.ImagePreviewBinding
import com.icyrockton.school_app.fragment.email.EmailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File
import java.io.FileOutputStream

class ImagePreviewDialogFragment : DialogFragment() {

    private val viewmodel: EmailViewModel by sharedViewModel()
    private var currentImageUrl = ""
    private lateinit var adapter: ImageAdapter
    private lateinit var binding: ImagePreviewBinding
    private val imageLoader: ImageLoader by inject()
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
        binding.imageShare.setOnClickListener { initImageShare() }
    }

    private fun initImageSave() {//图片保存
        val imageUrl = viewmodel.getImageUrl(currentIndex)
        imageUrl?.let {

            lifecycleScope.launch(Dispatchers.IO) {
                val (title, result) = getImage(imageUrl)
                result.drawable?.let {
                    val saveUri = saveImage(it as BitmapDrawable, title,false)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "图片已保存 $saveUri",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initImageShare() {//图片分享
        val imageUrl = viewmodel.getImageUrl(currentIndex)
        imageUrl?.let {
            lifecycleScope.launch {
                val (title, result) = getImage(imageUrl)
                result.drawable?.let {
                    val saveUri = saveImage(it as BitmapDrawable, title,true)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, saveUri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        type = "image/*"
                    }
                    startActivity(Intent.createChooser(intent, "分享图片~"))
                }
            }
        }
    }

    private suspend fun getImage(imageUrl: String): Pair<String, RequestResult> {
        val startIndex = imageUrl.lastIndexOf('/') + 1
        val endIndex = imageUrl.lastIndexOf('.')
        val title = imageUrl.substring(startIndex, endIndex)

        val request = GetRequest.Builder(requireContext()).data(imageUrl).build()
        val result = imageLoader.execute(request)
        return Pair(title, result)
    }

    private val imageSaveLocation by lazy {
        "${requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/${getString(R.string.app_name)}/"
    }

    suspend fun saveImage(bitmapDrawable: BitmapDrawable, title: String, isShare: Boolean) =
        withContext(Dispatchers.IO) {//保存图片
            val bitmap = bitmapDrawable.bitmap
            val dirfile = File(imageSaveLocation)
            if (!dirfile.exists()) {
                dirfile.mkdirs()
            }
            val file = File(imageSaveLocation, "$title.webp")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fos)
            fos.flush()
            fos.close()
            MediaStore.Images.Media.insertImage(
                requireContext().contentResolver,
                file.absolutePath,
                title,
                ""
            )
            if (isShare)
                return@withContext FileProvider.getUriForFile(
                    requireContext(),
                    "com.icyrockton.fileprovider",
                    file
                )
            return@withContext Uri.fromFile(file)
        }

    companion object {
        private const val TAG = "ImagePreviewDialogFragm"

    }
}