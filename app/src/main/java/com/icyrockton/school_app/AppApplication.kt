package com.icyrockton.school_app

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.icyrockton.school_app.fragment.LoginViewModel
import com.icyrockton.school_app.fragment.login.LoginModule
import com.icyrockton.school_app.fragment.main.mainModule
import com.icyrockton.school_app.fragment.profile.profileModule
import com.icyrockton.school_app.fragment.score.scoreModule
import com.icyrockton.school_app.fragment.second_class.secondClassModule
import com.icyrockton.school_app.module.HelperModule
import com.icyrockton.school_app.module.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application(){


    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@AppApplication)
            modules(NetworkModule,HelperModule)

            modules(LoginModule)

            modules(scoreModule)

            modules(mainModule)

            modules(profileModule)

            modules(secondClassModule)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}