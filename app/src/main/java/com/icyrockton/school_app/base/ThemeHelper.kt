package com.icyrockton.school_app.base

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.icyrockton.school_app.MainActivity
import com.icyrockton.school_app.R
import org.koin.android.ext.android.inject


//切换主题助手
class ThemeHelper(private val context: Context, private val helper: SharedPreferencesHelper) {
    companion object {
        private const val TAG = "ThemeHelper"
        val themes = listOf<ThemeWrapper>(
            ThemeWrapper(
                "咸蛋黄",
                R.style.yellow_egg_AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "海洋蓝",
                R.style.ocean_blue_AppTheme,
                R.color.ocean_blue_colorPrimary,
                R.color.ocean_blue_colorPrimaryDark,
                R.color.ocean_blue_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),
            ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            ),ThemeWrapper(
                "咸蛋黄",
                R.style.AppTheme,
                R.color.yellow_egg_colorPrimary,
                R.color.yellow_egg_colorPrimaryDark,
                R.color.yellow_egg_colorAccent
            )




        )
    }

    fun getCurrentThemeID():Int{
        val index = helper.getInt(context.getString(R.string.sh_current_theme_index), 0)
        return themes[index].styleID
    }
    fun applyTheme(activity: Activity) {
        val darkModeOn = helper.getBoolean(context.getString(R.string.sh_dark_mode), false)
        if (darkModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val index =
            helper.getInt(context.getString(R.string.sh_current_theme_index), 0)
        activity.setTheme(themes[index].styleID)
        activity.recreate()
    }
    fun initTheme(activity: Activity){
        val darkModeOn = helper.getBoolean(context.getString(R.string.sh_dark_mode), false)
        if (darkModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val index =
            helper.getInt(context.getString(R.string.sh_current_theme_index), 0)
        activity.setTheme(themes[index].styleID)
    }
}