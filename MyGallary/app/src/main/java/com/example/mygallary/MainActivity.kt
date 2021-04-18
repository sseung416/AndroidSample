package com.example.mygallary

import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                null,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") //찍은 날짜 내림차순
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getAllPhotos()
                } else {
                    Toast.makeText(null, "권한 거부됨", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}