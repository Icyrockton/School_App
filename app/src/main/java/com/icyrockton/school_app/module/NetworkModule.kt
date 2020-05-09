package com.icyrockton.school_app.module

import coil.ImageLoaderBuilder
import com.icyrockton.school_app.fragment.second_class.SecondClassPostResultJsonAdapter
import com.icyrockton.school_app.network.NetworkAPI
import com.squareup.moshi.Moshi
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.math.sign

val NetworkModule = module {
    single { OkHttpClient.Builder().cookieJar(get<NetWorkCookie>()).build() }

    single { NetWorkCookie() }

    single {
        ImageLoaderBuilder(androidContext()).crossfade(true).okHttpClient(get<OkHttpClient>())
            .build()
    }

    single { Moshi.Builder().build() }

    single { Retrofit.Builder().baseUrl("http://jwc.swjtu.edu.cn").client(get()).addConverterFactory(MoshiConverterFactory.create(get())).build() }

    single { get<Retrofit>().create(NetworkAPI::class.java) }

}