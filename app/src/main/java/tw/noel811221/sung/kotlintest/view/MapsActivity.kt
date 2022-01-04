package tw.noel811221.sung.kotlintest.view

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import tw.noel811221.sung.kotlintest.R
import tw.noel811221.sung.kotlintest.viewModel.MapActivityVM
import java.util.*

class MapsActivity : FragmentActivity(), GoogleMap.OnMarkerClickListener {
    private lateinit var geocoder: Geocoder
    private var latlng: LatLng = LatLng(23.551484, 120.873749)

    private lateinit var googleMap: GoogleMap
    private lateinit var mapActivityVM: MapActivityVM

    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener =
        GoogleMap.OnCameraIdleListener {
            //先清除全部
            googleMap.clear()
            //以地圖中心
            latlng = googleMap.cameraPosition.target
            //做地址查詢 並以該城市 為參數去訪問cafe列表API
            mapActivityVM.getLocationCafeShop(geocoder, latlng)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        geocoder = Geocoder(this, Locale.US)

        mapActivityVM = ViewModelProvider(this)[MapActivityVM::class.java]
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(
            OnMapReadyCallback {
                this.googleMap = it
                initMap()
                initObserve()
                initListener()
            })
    }


    private fun initMap() {
        //自訂圖資
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.google_map_style))
        //初始位置 顯示整個台灣
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7.5f))
    }


    private fun initObserve() {
        mapActivityVM.cafeListLiveData.observe(this, androidx.lifecycle.Observer {
            it.forEach { cafeItem ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(cafeItem.latitude, cafeItem.longitude))
                        .title(cafeItem.name)
                )
            }
        })

        mapActivityVM.errorLiveData.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListener() {
        googleMap.setOnCameraIdleListener(onCameraIdleListener)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return true
    }
}