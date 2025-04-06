package ru.mesler.androidworks.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val restModule = module {
    factory { provideRetrofit(get()) }
    single { provideNetworkApi(get()) }
}

fun provideRetrofit(context: Context): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/v1.4/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().apply {
            addInterceptor {
                Interceptor { chain ->
                    val request: Request = chain.request()
                    val url: HttpUrl = request.url.newBuilder()
                        .build()
                    chain.proceed(
                        request.newBuilder()
                            .url(url)
                            .addHeader("X-API-KEY", "KW79Y49-4NZ46JV-PYV9D9F-7RWHB5W")
                            .build()
                    )
                }.intercept(it)
            }
            addInterceptor(ChuckerInterceptor(context))
        }.build())
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): MovieApi =
    retrofit.create(MovieApi::class.java)
