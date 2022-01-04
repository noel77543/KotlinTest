package tw.noel811221.sung.kotlintest.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tw.noel811221.sung.kotlintest.BuildConfig

object ApiClient {

    private var client :OkHttpClient =  OkHttpClient.Builder().build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.DOMAIN)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    public fun <T> getCafeApiCenter(apicenter: Class<T>): T {
        return retrofit.create(apicenter)
    }


}