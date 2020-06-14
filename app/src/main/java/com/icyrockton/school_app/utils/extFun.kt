package com.icyrockton.school_app.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.icyrockton.school_app.R


fun Fragment.toast(text: CharSequence) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun Fragment.toast(@StringRes resId: Int) =
    Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()

fun Fragment.toastLong(text: CharSequence) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Fragment.toastLong(@StringRes resId: Int) =
    Toast.makeText(requireContext(), resId, Toast.LENGTH_LONG).show()

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

//获取R.attr 中的颜色....
fun Context.getThemeColor(@AttrRes attrRes: Int):Int{
    val typedValue = TypedValue()
    theme.resolveAttribute (attrRes, typedValue, true)
    return typedValue.data
}