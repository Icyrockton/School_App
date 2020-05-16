package com.icyrockton.school_app.fragment.email.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import coil.ImageLoader
import coil.request.LoadRequest
import com.icyrockton.school_app.R
import org.wordpress.aztec.Html

class CoilImageGetter(
    private val view: View,
    private val imageLoader: ImageLoader,
    private val context: Context
) : Html.ImageGetter {
    override fun loadImage(source: String?, callbacks: Html.ImageGetter.Callbacks?, maxWidth: Int) {
        loadImage(source, callbacks, maxWidth)
    }

    override fun loadImage(
        source: String?,
        callbacks: Html.ImageGetter.Callbacks?,
        maxWidth: Int,
        minWidth: Int
    ) {
        val request = LoadRequest.Builder(context).data(source).listener()
            .placeholder(R.drawable.ic_email_image_loading)
            .target(
                onStart = { placeholder: Drawable? ->
                    callbacks?.onImageLoading(placeholder)
                },
                onSuccess = { result: Drawable ->
                    if (result is BitmapDrawable) {
                        val factor:Double = (view.width).toDouble() / (result.bitmap.width).toDouble()
                        val height = factor * result.bitmap.height
                        callbacks?.onImageLoaded(
                            BitmapDrawable(
                                context.resources,
                                Bitmap.createScaledBitmap(
                                    result.bitmap,
                                    view.width,
                                    height.toInt(),
                                    true
                                )
                            )
                        )
                    }
                },
                onError = {
                    callbacks?.onImageFailed()
                }
            ).build()

        imageLoader.execute(request)
    }

    companion object {
        private const val TAG = "CoilImageGetter"
    }
}