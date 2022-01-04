package tw.noel811221.sung.kotlintest.repository

import tw.noel811221.sung.kotlintest.utils.ApiCallback
import tw.noel811221.sung.kotlintest.utils.ApiClient
import tw.noel811221.sung.kotlintest.utils.CafeApiCenter

object ApiRepository {

    public fun connectToGetCafeList(cityName:String,callback: ApiCallback){
        ApiClient.getCafeApiCenter(CafeApiCenter::class.java)
            .getStationData(cityName)
            .enqueue(callback)
    }
}