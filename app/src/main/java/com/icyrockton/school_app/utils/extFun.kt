package com.icyrockton.school_app.utils

import android.graphics.Bitmap
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun Fragment.toast(text: CharSequence) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun Fragment.toast(@StringRes resId: Int) =
    Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()

fun Fragment.toastLong(text: CharSequence) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Fragment.toastLong(@StringRes resId: Int) =
    Toast.makeText(requireContext(), resId, Toast.LENGTH_LONG).show()

