package com.example.task.data

import com.example.task.BuildConfig
import com.example.task.common.Api
import com.example.task.common.Constant
import com.example.task.common.util.PefUtil
import com.example.task.data.response.InfoResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LoginFactory {

    companion object {
        private const val REQUEST_TIMEOUT = 15L
        fun create(BASE_URL: String = "http://api.onmobi.vn/v1/"): LoginFactory? {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("authorization", "Bearer xyz")
                        .addHeader("User-Agent", PefUtil.getString(Constant.SETTING_AGENT))
                        .addHeader("Origin", "http://onmobi.vn")
                        .addHeader("Referer", "http://onmobi.vn/")
                        .addHeader("Accept", "*/*")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build()
                    chain.proceed(newRequest)
                }

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
            }

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClientBuilder.build())
                .build()

            return retrofit.create(LoginFactory::class.java)
        }
    }

    @POST(Api.LOGIN_ONMOBI)
    fun login(@Body params: HashMap<String, String>): Single<InfoResponse>


}