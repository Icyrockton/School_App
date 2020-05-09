package com.icyrockton.school_app.base

import android.content.Context
import android.content.SharedPreferences
import com.icyrockton.school_app.R

class SharedPreferencesHelper(private val context: Context) {

    companion object {
        private const val TAG = "SharePreferenceHelper"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.sh_name), Context.MODE_PRIVATE)

    fun edit(
        commit: Boolean = true,
        action: SharedPreferences.Editor.() -> Unit
    ) {
        val editor = sharedPreferences.edit()
        action(editor)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    fun getBoolean(key: String, defValue: Boolean)=sharedPreferences.getBoolean(key,defValue)

    fun getFloat(key: String, defValue: Float)=sharedPreferences.getFloat(key,defValue)

    fun getInt(key: String, defValue: Int)=sharedPreferences.getInt(key,defValue)

    fun getLong(key: String, defValue: Long)=sharedPreferences.getLong(key,defValue)

    fun getString(key: String, defValue: String?)=sharedPreferences.getString(key,defValue)!!

    fun getStringSet(
        key: String?,
        defValues: Set<String?>?
    )=sharedPreferences.getStringSet(key,defValues)!!

}