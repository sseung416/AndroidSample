package com.example.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

//구글 지도를 사용하려면 API 키를 발급받아야함 (values > google_maps_api.xml)

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //위치 정보를 주기적으로 얻는 데 필요한 객체 선언
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest //위치 요청 객체
    private lateinit var locationCallback: LocationCallback //위치 갱신되면 호출되는 콜백

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    //구글 지도에서 지원하는 이동 자취를 그리는 메서드
    //addPolyLine(): 선의 집합, 지도에 경로와 노선 표시
    //addCircle(): 원 표시
    //addPolygon(): 영역 표시

    //PolyLine 옵션: 굵기5f, 빨간 색
    //PolyLineOptions() 객체로 선 굵기, 색상 등 설정 ㄱㄴ
    private val polylineOptions = PolylineOptions().width(10f).color(Color.RED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //화면 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //세로 모드로 화면 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_maps)

        //SupportMapFragment를 가져와 지도가 준비가 되면 확인
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationIit()
    }

    //위치 정보를 얻기 위한 각종 초기화
    private fun locationIit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()

        //priority: 정확도 (가장 정확, 블록 수준 정확, 도시 수준 정확, 추가 전력 소모 x 최상의 정확도)
        //interval: 위치 갱신시 필요한 시간 밀리초 단위로 입력
        //fastestInterval: 다른 앱에서 위치 갱신 했을 때 그 정보를 가장 빠른 간격으로 입력
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        //가장 정확한 위치 요구하면서 10초마다 위치 정보 갱신하고 다른앱에서 위 갱신했다면 5초마다 확인
    }

    //위치정보를 주기적으로 요청하는 코드는 액티비티가 화면에 보일 때만 수행하는 게 좋음
    //onResum()에서 위치 정보 요청, onPause()에서 위치정보를 삭제
    override fun onResume() {
        super.onResume()

        //권한 요청
        permissionCheck(cancle = {
            //위치 정보가 필요한 이유를 다이얼 로그에 표시
            showPermissionInfoDialog()
        }, ok = {
            //위치 정보를 주기적으로 요청함 (권한 필요)
            addLocationListenr()
        })

    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener() {
        //현재 위치 요청을 삭제
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission") //권한 요청을 무시하는 주석
    private fun addLocationListenr() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            //14 level로 확대하며 현재 위치로 화면 이동
            location?.run {
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f))

                Log.d("MapsActivity", "위도: $latitude, 경도 $longitude")
                //PolyLine에 좌표 추가
                polylineOptions.add(latLng)

                //선그리기
                mMap.addPolyline(polylineOptions)
            }
        }
    }

    //고차함수: 함수의 인자로 함수를 전달하거나 반환
    private fun permissionCheck(cancle: () -> Unit, ok: () -> Unit) { //Unit == void
        //위치 권한 검사
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //이전에 권한이 한 번 거부한 적이 있을 때 실행
                cancle()
            } else {
                //권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            //권한 수락했을 때 실행
            ok()
        }
    }

    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요한 이유")
            .setMessage("현재 위치 정보를 얻으려면 위치 권한이 필요합니다.")
            .setPositiveButton("확인") { _, _ ->
                //권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("취소") { _, _ -> }
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //권한 허용됨
                    addLocationListenr()
                } else {
                    //권한 거부
                    Toast.makeText(null, "권한 거부 됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}