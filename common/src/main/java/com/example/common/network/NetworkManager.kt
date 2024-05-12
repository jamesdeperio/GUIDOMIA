package com.example.common.network

import android.content.Context
import com.example.common.BuildConfig
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import jdp.retrofitkit.RetrofitManager
import jdp.retrofitkit.SerializationFormatFactory
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager(context: Context) : RetrofitManager(context=context) {
    override fun initBaseURL(): String = BuildConfig.BASE_URL

    override fun initCacheSize(): Int = 0

    override fun initConnectTimeOut(): Long = 60

    override fun initReadTimeOut(): Long = 60

    override fun initWriteTimeOut(): Long = 60

    override fun isPrintLogEnabled(): Boolean = BuildConfig.DEBUG

    override fun initCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    override fun initConverterFactory(): Converter.Factory {
        val tikXMLConverter =
            TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())
        val gsonConverter = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        return SerializationFormatFactory.Builder()
            .setXMLConverterFactory(converterFactory = tikXMLConverter)
            .setJSONConverterFactory(converterFactory = gsonConverter)
            .build()
    }


    override fun OkHttpClient.Builder.interceptorConfiguration(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(NetworkErrorInterceptor())
        return builder
    }

}