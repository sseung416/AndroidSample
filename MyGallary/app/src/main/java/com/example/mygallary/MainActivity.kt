package com.example.mygallary

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    private lateinit var viewPager : ViewPager2
    private var timerTask : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)

        //permisson

        //위험 권환이 필요한 작업 -> 수행할 때마다 권한 확인 필요 -> ContextCompat.checkSelfPermission()
        //권한이 부여되었는지 확인: (권한 O) PERMISSION_GRANTED, (권한 X) PERMISSION_DENIED 반환
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //권한이 허용되지 않음

            //shouldShowRequestPermissionRationale() : 사용자가 전에 권한 요청을 거부했는지 반환
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                AlertDialog.Builder(this)
                        .setTitle("권한이 필요한 이유")
                        .setMessage("사진 정보를 얻으려면 외부 저장소 권환이 필수로 필요합니다.")
                        .setPositiveButton("확인") { _, _ ->
                            //권한 요청
                            ActivityCompat.requestPermissions(this,
                                    //arrayOf() : 다양한 타입의 데이터를 넣을 수 있음. Array 타입의 배열 객체를 변환함
                                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                                    REQUEST_READ_EXTERNAL_STORAGE)
                        }
                        .setNegativeButton("취소") { _, _ -> }
                        .show()
            } else {
                //권한 요청

                //requestPermissions() : 외부 저장소 읽기 권한 요청
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            //권한이 이미 허용됨
            getAllPhotos()
        }
    }

    private fun getAllPhotos() {
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, //가져올 항목 배열
                null, //조건
                null, //조건
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") //찍은 날짜 내림차순

        //아이템이 프래그먼트인 ArrayList 생성
        val fragments = ArrayList<Fragment>()

        if(cursor != null) {
            while(cursor.moveToNext()) {
                //사진 경로 URI 가져오기
                var uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)

                //cursor 객체에서 사진 가져올때마다 Fragment.newInstance()로 프래그먼트 생성 후 ArrayList()에 추가
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()

        //MyPagerAdapter를 생성 -> 프래그먼트 매니저 생성해 인자로 전달
        //프래그먼트 매니저는 getSupportFragmentManager() 메서드로 가져올 수 있고, supportFragmentManger 프로퍼티로 접근
        val adapter = MyPagerAdapter(supportFragmentManager, _)
        //updateFragments(): 프래그먼트 리스트 전달
        adapter.updateFragments(fragments)
        //어댑터를 viewPager에 설정
        viewPager.adapter = adapter


            //3초마다 자동 슬라이드
            timerTask = timer(period = 3000) {
                runOnUiThread {
                    //현재 페이지가 마지막이 아니라면 다음 UI로 변경
                    if(viewPager.currentItem < adapter.itemCount - 1) {
                        viewPager.currentItem = viewPager.currentItem + 1
                    } else { //마지막 페이지면 첫 페이지로로                        viewPager.currentItem = 0
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getAllPhotos()
                } else {
                    Toast.makeText(null, "권한 거부됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }
}