package tw.noel811221.sung.kotlintest.useCase

import tw.noel811221.sung.kotlintest.repository.ApiRepository
import tw.noel811221.sung.kotlintest.utils.ApiCallback

class MapUseCase {

    public fun connectToGetCafeList(cityName:String,callback: ApiCallback){
        ApiRepository.connectToGetCafeList(cityName, callback)
    }

}