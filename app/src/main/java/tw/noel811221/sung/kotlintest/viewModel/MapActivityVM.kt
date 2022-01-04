package tw.noel811221.sung.kotlintest.viewModel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import tw.noel811221.sung.kotlintest.model.map.Cafe
import tw.noel811221.sung.kotlintest.model.map.CafeItem
import tw.noel811221.sung.kotlintest.useCase.MapUseCase
import tw.noel811221.sung.kotlintest.utils.ApiCallback
import java.io.IOException

class MapActivityVM(application: Application) : AndroidViewModel(application) {

    private var mapUseCase: MapUseCase = MapUseCase()
     var cafeListLiveData: MutableLiveData<Cafe> = MutableLiveData()
     var errorLiveData : MutableLiveData<String> = MutableLiveData()

    init {

    }

    public fun getLocationCafeShop(geocoder: Geocoder, latLng: LatLng) {
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                if(!TextUtils.isEmpty(address.subAdminArea) && address.subAdminArea.contains("County")){
                    val name : String = address.subAdminArea.substring(0,address.subAdminArea.indexOf("County")).trim()
                    connectToGetCafeList(name)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun connectToGetCafeList(cityName: String) {
        mapUseCase.connectToGetCafeList(cityName, object : ApiCallback(EVENT_CAFE) {
            override fun onSuccess(response: String, event: Int) {
                val cafe :Cafe = Gson().fromJson(response,Cafe::class.java)
                cafeListLiveData.postValue(cafe)
            }

            override fun onFailed(message: String, event: Int) {
                errorLiveData.postValue(message)
            }
        })
    }
}