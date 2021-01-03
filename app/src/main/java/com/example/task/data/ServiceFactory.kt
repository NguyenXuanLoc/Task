package com.example.task.data

import com.example.task.BuildConfig
import com.example.task.common.Api
import com.example.task.data.response.DataResponse
import com.example.task.data.response.SettingResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.task.data.response.BaseListResponse
import vn.vano.vicall.data.response.BaseResponse
import java.util.concurrent.TimeUnit

interface ServiceFactory {

    companion object {
        private const val REQUEST_TIMEOUT = 15L

        fun create(BASE_URL: String = "http://onmb.vano.vn/v1/onmb/"): ServiceFactory {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

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

            return retrofit.create(ServiceFactory::class.java)
        }
    }

    @FormUrlEncoded
    @POST(Api.DATA)
    fun sendData(@FieldMap params: HashMap<String, String>): Single<BaseResponse<String?>>


    @GET(Api.GET_PARTNER_CODE)
    fun getPartnerCode(): Single<BaseListResponse<String>>

    @GET(Api.GET_DATA)
    fun getData(@QueryMap params: HashMap<String, String>): Single<DataResponse>

    @GET(Api.GET_SETTING)
    fun getSetting(): Single<BaseListResponse<SettingResponse>>

    @FormUrlEncoded
    @POST(Api.LOG_ACCOUNT)
    fun logAccount(@FieldMap params: HashMap<String, String>): Single<BaseResponse<String>>

    @FormUrlEncoded
    @POST(Api.LOG_ACTION)
    fun logAction(@FieldMap params: HashMap<String, String>): Single<BaseResponse<String>>

    @POST(Api.LOGIN_ONMOBI)
    fun login(@Body params: HashMap<String, String>): Single<BaseResponse<String>>


}