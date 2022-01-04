package tw.noel811221.sung.kotlintest.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import tw.noel811221.sung.kotlintest.BuildConfig

interface CafeApiCenter {

    //咖啡店列表
    @GET(BuildConfig.API_CAFE)
    fun getStationData(@Path("cityName") cityName:String): Call<ResponseBody>

}